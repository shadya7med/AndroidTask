package com.example.librarytask.modules.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.librarytask.data.document.Docs

class DetailsViewModel : ViewModel() {


    private val TAG = this.javaClass.simpleName

    val selectedDoc = MutableLiveData<Docs?>()
    val isbnList = Transformations.map(selectedDoc) {
        if (it?.isbn != null && it.isbn.size > 5) it.isbn.subList(0, 5) else it?.isbn
    }
}