package com.hsh.booksearcher.listener

import com.hsh.booksearcher.constants.Constants
import com.hsh.booksearcher.domain.model.BookData
import com.hsh.booksearcher.viewmodel.view.adapter.MainPagingAdapter

class GlobalResource {
    companion object {
        lateinit var mBookInfo:BookData
        lateinit var mMainAdapter:MainPagingAdapter
        lateinit var mMainActivityListener:MainActivityListener
        var          mPreFragmentState:String     = Constants.FRAGMENT_STATE_SEARCH
        var          mCurrentFragmentState:String = Constants.FRAGMENT_STATE_SEARCH
    }
}