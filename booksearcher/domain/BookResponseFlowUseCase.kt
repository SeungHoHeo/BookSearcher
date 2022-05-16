package com.hsh.booksearcher.domain



import androidx.paging.map
import com.hsh.booksearcher.data.repository.SearchRepository
import com.hsh.booksearcher.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookResponseFlowUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @DispatcherModule.DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(query: String) = searchRepository.searchBook(query)
        .map { pagingData -> pagingData.map { repo -> repo.toRepoInfo() } }
        .flowOn(defaultDispatcher)
}