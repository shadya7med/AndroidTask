package com.example.librarytask.modules.home

import Entry
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarytask.data.document.source.remote.SearchRemoteDataSource
import kotlinx.coroutines.launch

class HomeViewModel(private val searchRemoteDataSource: SearchRemoteDataSource) : ViewModel() {


    private val TAG = this.javaClass.simpleName

    val loading = MutableLiveData<Boolean?>()
    val showSnackbar = MutableLiveData<String?>()


    val docsList = MutableLiveData<List<DocumentsAdapter.DataItemHolder>?>()
    val entriesList = MutableLiveData<List<Entry>?>()
    val listVisibility = Transformations.map(entriesList) {
        if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    var numFound: Long? = null
    var pageIndex = 1

    fun searchDocs(query: String) {
        if (pageIndex == 1) {
            loading.value = true
        }
        viewModelScope.launch {
            try {
                val response =
                    searchRemoteDataSource.getSearchedDocs(query = query, page = pageIndex)
                val docs = response.docs
                numFound = response.num_found
                loading.value = false
                if (pageIndex == 1) {
                    docsList.value = docs?.map { DocumentsAdapter.DataItemHolder.DocsHolder(it) }
                } else {
                    docs?.let { list ->
                        val currentList =
                            docsList.value?.filterIsInstance<DocumentsAdapter.DataItemHolder.DocsHolder>()
                                ?.toMutableList()
                        currentList?.addAll(list.map { DocumentsAdapter.DataItemHolder.DocsHolder(it) })
                        docsList.value = currentList
                    }
                }
            } catch (e: Exception) {
                handleDataError(e)
            }
        }
    }

    fun loadMore(query: String) {
        if (docsList.value != null && numFound != null && numFound != 0L) {
            if (docsList.value!!.size < numFound!!) {
                pageIndex += 1
                val currentList = docsList.value?.toMutableList()
                currentList?.add(DocumentsAdapter.DataItemHolder.FooterHolder())
                docsList.value = currentList
                searchDocs(query)

            }
        }
    }

    private fun handleDataError(exception: Exception) {
        loading.value = false
        showSnackbar.value = exception.localizedMessage
        Log.e(TAG, exception.localizedMessage)
    }

    fun getAuthorWorks(authorKey: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = searchRemoteDataSource.getAuthorsWorks(authorKey)
                loading.value = false
                entriesList.value = response.entries
            } catch (e: Exception) {
                handleDataError(e)
            }
        }
    }

    fun getBookDetails(key: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = searchRemoteDataSource.getBookDetails(key)
                loading.value = false
                entriesList.value = listOf(response)
            } catch (e: Exception) {
                handleDataError(e)
            }
        }
    }


}