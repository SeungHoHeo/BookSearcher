package com.hsh.booksearcher.viewmodel.view.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hsh.booksearcher.R
import com.hsh.booksearcher.domain.model.BookData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("bindIcon")
fun ImageView.bindIcon(resId: Int?) {
    resId?.let {
        this.setBackgroundResource(resId)
    }
}

@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: PagingData<BookData>?) {
    CoroutineScope(Dispatchers.IO).launch {
        list?.let {
            (adapter as? MainPagingAdapter)?.submitData(it)
        }
    }
}

@BindingAdapter("bindImage")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        Glide.with(context)
            .load(imgUrl)
            .apply(RequestOptions()
                .transform(RoundedCorners(20))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(this)
    }
}

@BindingAdapter("setErrorMsg")
fun TextView.setErrorMsg(loadState: LoadState) {
    if (loadState is LoadState.Error) {
        this.text = loadState.error.localizedMessage
    }
}

@BindingAdapter("setVisibility")
fun View.setVisibility(loadState: LoadState) {
    when (this) {
        is ProgressBar -> this.isVisible = loadState is LoadState.Loading
        is Button -> this.isVisible = loadState is LoadState.Error
        is TextView -> this.isVisible = loadState is LoadState.Error
    }
}