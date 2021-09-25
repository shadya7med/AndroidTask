package com.example.librarytask.data.document.source.remote

import AuthorWorksResponse
import Entry
import com.example.librarytask.Constants
import com.example.librarytask.data.document.DocumentsSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchAPIService {
    @GET(Constants.SEARCH)
    suspend fun getSearchedDocs(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int
    ): DocumentsSearchResponse

    @GET("{key}" + ".json")
    suspend fun getBookDetails(@Path("key") key: String): Entry

    @GET(Constants.AUTHORS + "/{key}/" + Constants.WORKS_JSON)
    suspend fun getAuthorsWorks(
        @Path("key") authorKey: String,
        @Query("limit") limit: Int = 40
    ): AuthorWorksResponse
}