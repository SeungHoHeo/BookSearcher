package com.hsh.booksearcher.data.service
import com.hsh.booksearcher.constants.Constants
import com.hsh.booksearcher.data.dto.BookDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("v3/search/book")
    suspend fun searchBook(
        @Header("Authorization") apiKey: String = Constants.AUTH_HEADER,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: String,
        @Query("size") size: String,
    ): BookDto
}