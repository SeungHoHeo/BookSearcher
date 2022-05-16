package com.hsh.booksearcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hsh.booksearcher.domain.BookResponseFlowUseCase
import com.hsh.booksearcher.domain.model.BookData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repoFlowUseCase: BookResponseFlowUseCase,
) : ViewModel() {
    private val _repoList = MutableStateFlow<PagingData<BookData>>(PagingData.empty())
    val repoList = _repoList.asStateFlow()

    fun getRepoList(query: String) {
        viewModelScope.launch {
            repoFlowUseCase(query)
                .cachedIn(viewModelScope)
                .collectLatest { repoInfo -> _repoList.emit(repoInfo) }
        }
    }
}