package com.example.librarytask.data.document.source.remote

import com.example.librarytask.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchRemoteDataSource() {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    private val searchAPIService = retrofit.create(SearchAPIService::class.java)

    suspend fun getSearchedDocs(query: String, page: Int) =
        searchAPIService.getSearchedDocs(query = query, page = page)

    suspend fun getBookDetails(key: String) = searchAPIService.getBookDetails(key)

    suspend fun getAuthorsWorks(authorKey: String) =
        searchAPIService.getAuthorsWorks(authorKey)

}