package com.hsh.booksearcher

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.hsh.booksearcher.constants.Constants
import com.hsh.booksearcher.databinding.ActivityMainBinding
import com.hsh.booksearcher.listener.GlobalResource
import com.hsh.booksearcher.listener.MainActivityListener
import com.hsh.booksearcher.viewmodel.view.DetailResultFragment
import com.hsh.booksearcher.viewmodel.view.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityListener {
    private lateinit var binding: ActivityMainBinding
    private var flag by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GlobalResource.mMainActivityListener = this
        showFragment(Constants.FRAGMENT_STATE_SEARCH)
    }


    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun showFragment(viewId: String) {
        GlobalResource.mPreFragmentState = GlobalResource.mCurrentFragmentState

        if (viewId == Constants.FRAGMENT_STATE_SEARCH) {
            if (GlobalResource.mPreFragmentState != Constants.FRAGMENT_STATE_DETAIL_RESULT) {
                supportFragmentManager.commit(true) {
                    replace(R.id.search_fragment_container_view, SearchFragment())
                }
            } else {
                binding.resultFragmentContainerView.visibility = View.GONE
                binding.searchFragmentContainerView.visibility = View.VISIBLE
                SearchFragment().refreshLikeIcon()
            }
        } else {
            binding.resultFragmentContainerView.visibility = View.VISIBLE
            binding.searchFragmentContainerView.visibility = View.GONE
            supportFragmentManager.commitNow(true) {
                setCustomAnimations(R.anim.item_anim_fall_down,R.anim.item_anim_fall_down,R.anim.item_anim_fall_down,R.anim.item_anim_fall_down)
                replace(R.id.result_fragment_container_view,DetailResultFragment())
            }
        }
        GlobalResource.mCurrentFragmentState = viewId
    }


    override fun onBackPressed() {
        if (GlobalResource.mCurrentFragmentState == Constants.FRAGMENT_STATE_SEARCH) {
            super.onBackPressed()
            finishAffinity()
        } else {
            showFragment(Constants.FRAGMENT_STATE_SEARCH)
        }

    }


    override fun onSetView(viewId: String) {
        showFragment(viewId)
    }
}