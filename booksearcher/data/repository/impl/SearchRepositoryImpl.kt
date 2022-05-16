package com.hsh.booksearcher.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hsh.booksearcher.data.dto.BookResponse


import com.hsh.booksearcher.data.paging.SearchPagingSource
import com.hsh.booksearcher.data.repository.SearchRepository
import com.hsh.booksearcher.data.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val service: SearchService,
) : SearchRepository {

    companion object {
        private const val PAGE_SIZE = 50
    }

    override fun searchBook(query: String): Flow<PagingData<BookResponse>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            SearchPagingSource(query, service)
        }.flow
}