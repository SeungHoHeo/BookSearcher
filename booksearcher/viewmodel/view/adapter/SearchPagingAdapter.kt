package com.hsh.booksearcher.viewmodel.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hsh.booksearcher.domain.model.BookData
import com.hsh.booksearcher.R
import com.hsh.booksearcher.constants.Constants
import com.hsh.booksearcher.databinding.ItemMainBinding
import com.hsh.booksearcher.listener.GlobalResource

class MainPagingAdapter
    : PagingDataAdapter<BookData, MainViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookData>() {
            override fun areItemsTheSame(
                oldItem: BookData,
                newItem: BookData,
            ): Boolean = oldItem.thumbnail == newItem.thumbnail

            override fun areContentsTheSame(
                oldItem: BookData,
                newItem: BookData,
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_main,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_anim_fall_down)
            holder.bind(it)
        }
    }
}

class MainViewHolder(
    private val binding: ItemMainBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookData) {
        binding.root.setOnClickListener {
            GlobalResource.mBookInfo = item
            GlobalResource.mMainActivityListener.onSetView(Constants.FRAGMENT_STATE_DETAIL_RESULT)
        }
        binding.apply {
            bookInfo = item
            executePendingBindings()
        }
    }
}
