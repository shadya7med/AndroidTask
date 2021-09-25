package com.example.librarytask.modules.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.librarytask.data.document.source.remote.SearchRemoteDataSource


class HomeViewModelFactory(private val searchRemoteDataSource: SearchRemoteDataSource) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(searchRemoteDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}