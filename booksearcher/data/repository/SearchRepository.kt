package com.hsh.booksearcher.data.repository

import androidx.paging.PagingData
import com.hsh.booksearcher.data.dto.BookResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchBook(query: String): Flow<PagingData<BookResponse>>
}