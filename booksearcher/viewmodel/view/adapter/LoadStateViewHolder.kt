package com.hsh.booksearcher.viewmodel.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.hsh.booksearcher.R
import com.hsh.booksearcher.databinding.ItemLoadStateFooterBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateFooterBinding,
    retry: retry,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.loadState = loadState
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            return LoadStateViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_load_state_footer,
                parent,
                false
            ), retry)
        }
    }
}