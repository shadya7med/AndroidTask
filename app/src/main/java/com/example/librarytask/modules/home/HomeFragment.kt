package com.example.librarytask.modules.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.librarytask.R
import com.example.librarytask.data.document.source.remote.SearchRemoteDataSource
import com.example.librarytask.modules.details.DetailsFragment
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(SearchRemoteDataSource())
        )[HomeViewModel::class.java]
    }

    private lateinit var docsRv: RecyclerView
    private lateinit var entriesRv: RecyclerView
    private lateinit var searchEt: EditText
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSearchEditText(view)
        setupDocsRecyclerView(view)
        setupEntriesRecyclerView(view)
        observeViewModel()
    }

    private fun setupSearchEditText(view: View) {
        searchEt = view.findViewById(R.id.search_et)
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (before != count && (count + start) > 10) {
                    val query = s?.replace("\\s".toRegex(), "+")
                    query?.let {
                        viewModel.pageIndex = 1
                        viewModel.entriesList.value = listOf()
                        viewModel.searchDocs(query)
                    }

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun setupDocsRecyclerView(root: View) {
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            val query = searchEt.text.toString()
            if (query.isNotBlank()) {
                viewModel.pageIndex = 1
                viewModel.searchDocs(query)
            }
        }
        docsRv = root.findViewById(R.id.document_rv)
        docsRv.adapter = DocumentsAdapter(
            DocumentsAdapter.DocClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        it
                    )
                )
            }) {
            viewModel.loadMore(searchEt.text.toString())
        }

    }

    private fun setupEntriesRecyclerView(root: View) {
        entriesRv = root.findViewById(R.id.entries_rv)
        entriesRv.adapter = EntriesAdapter()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                swipeRefreshLayout.isRefreshing = it
                viewModel.loading.value = null
            }
        }
        viewModel.showSnackbar.observe(viewLifecycleOwner) {
            it?.let { errorMsg ->
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    errorMsg,
                    Snackbar.LENGTH_LONG
                ).show()

                viewModel.showSnackbar.value = null
            }
        }
        viewModel.docsList.observe(viewLifecycleOwner) {
            (docsRv.adapter as? androidx.recyclerview.widget.ListAdapter<*, *>)?.submitList(it as List<Nothing>?)
        }
        viewModel.entriesList.observe(viewLifecycleOwner) {
            (entriesRv.adapter as? androidx.recyclerview.widget.ListAdapter<*, *>)?.submitList(it as List<Nothing>?)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            DetailsFragment.SELECTED_AUTHOR_KEY
        )?.observe(
            viewLifecycleOwner
        ) { authorKey ->
            authorKey?.let {
                viewModel.getAuthorWorks(it)
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            DetailsFragment.SELECTED_Book_key
        )?.observe(
            viewLifecycleOwner
        ) { key ->
            key?.let {
                viewModel.getBookDetails(key)
            }
        }
        viewModel.listVisibility.observe(viewLifecycleOwner) {
            it?.let {
                docsRv.visibility = if (it == View.VISIBLE) View.GONE else View.VISIBLE
                entriesRv.visibility = it
            }
        }

    }

}