package com.hsh.booksearcher.viewmodel.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.hsh.booksearcher.R
import com.hsh.booksearcher.databinding.FragmentSearchBinding
import com.hsh.booksearcher.listener.GlobalResource
import com.hsh.booksearcher.viewmodel.SearchViewModel
import com.hsh.booksearcher.viewmodel.view.adapter.LoadStateAdapter
import com.hsh.booksearcher.viewmodel.view.adapter.MainPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var imm: InputMethodManager
    private val viewModel: SearchViewModel by viewModels()
    private var mCurrentKeyword:String = ""

    private lateinit var mainPagingAdapter: MainPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.layoutNothingText.visibility = View.VISIBLE
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        mainPagingAdapter = MainPagingAdapter()
        GlobalResource.mMainAdapter = mainPagingAdapter

        initViews()
        collectFlows()
    }

    private fun initViews() {
        binding.rvMain.adapter = mainPagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { mainPagingAdapter.retry() }
        )

        binding.rvMain.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                binding.etSearch.clearFocus()
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        mainPagingAdapter.addLoadStateListener { loadState ->
            binding.tvNothing.isVisible =
                loadState.refresh is LoadState.NotLoading && mainPagingAdapter.itemCount == 0 && binding.etSearch.text.isNotEmpty()
        }

    }
    fun refreshLikeIcon() {
        GlobalResource.mMainAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val handler = CoroutineExceptionHandler { _, e -> println("Caught $e") }
                supervisorScope {
                    launch(handler) {
                        mainPagingAdapter.loadStateFlow
                            .distinctUntilChangedBy { it.refresh }
                            .filter { it.refresh is LoadState.NotLoading }
                            .collect {
                                binding.rvMain.scrollToPosition(0)
                            }

                    }

                    launch(handler) {
                        textChanges()
                            .debounce(200)
                            .collectLatest { keyword ->
                                viewModel.getRepoList(keyword)
                            }
                    }

                    launch(handler) {
                        viewModel.repoList.collectLatest { repoInfo ->
                            mainPagingAdapter.submitData(repoInfo)
                        }
                    }
                }

            }
        }
    }

    private fun textChanges() = callbackFlow {
        val listener = binding.etSearch.doAfterTextChanged { keyword ->
            if (mCurrentKeyword != keyword.toString()) {
                trySend(keyword.toString())
                mCurrentKeyword = keyword.toString()
            }
        }
        awaitClose { binding.etSearch.removeTextChangedListener(listener) }
    }
}