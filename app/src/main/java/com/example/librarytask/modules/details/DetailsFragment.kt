package com.example.librarytask.modules.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.librarytask.R


class DetailsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
        )[DetailsViewModel::class.java]
    }

    private lateinit var isbnRv: RecyclerView
    private lateinit var title: TextView
    private lateinit var author: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        bindArguments()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupIsbnRecyclerView(view)
        setupInfoHolder(view)
        observeViewModel()
    }

    private fun bindArguments() {
        viewModel.selectedDoc.value = DetailsFragmentArgs.fromBundle(requireArguments()).selectedDoc
    }

    private fun setupIsbnRecyclerView(root: View) {
        isbnRv = root.findViewById(R.id.isbn_rv)
        isbnRv.adapter = IsbnAdapter()
    }

    private fun setupInfoHolder(view: View) {
        title = view.findViewById(R.id.docs_title_tv)
        title.apply {
            text = viewModel.selectedDoc.value?.title
            setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_Book_key,
                    viewModel.selectedDoc.value?.key
                )
                findNavController().navigateUp()
            }
        }
        author = view.findViewById(R.id.docs_author_tv)
        author.apply {
            text = viewModel.selectedDoc.value?.author_name?.get(0)
            setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_AUTHOR_KEY,
                    viewModel.selectedDoc.value?.author_author_key?.get(0)
                )
                findNavController().navigateUp()
            }
        }

    }

    private fun observeViewModel() {

        viewModel.isbnList.observe(viewLifecycleOwner) {
            (isbnRv.adapter as? androidx.recyclerview.widget.ListAdapter<*, *>)?.submitList(it as List<Nothing>?)
        }
    }

    companion object {
        const val SELECTED_AUTHOR_KEY = "selected_author_key"
        const val SELECTED_Book_key = "selected_title"
    }

}