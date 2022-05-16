package com.hsh.booksearcher.viewmodel.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hsh.booksearcher.R
import com.hsh.booksearcher.constants.Constants
import com.hsh.booksearcher.databinding.FragmentDetailBinding
import com.hsh.booksearcher.listener.GlobalResource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailResultFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setBookImageView(GlobalResource.mBookInfo?.thumbnail)
        setBookInfo()
        setListener()
        updateLikeIcon()
        return binding.root
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            GlobalResource.mMainActivityListener.onSetView(Constants.FRAGMENT_STATE_SEARCH)
        }

        binding.ivLike.setOnClickListener {
            if (GlobalResource.mBookInfo.like == R.mipmap.icon_empty_like)
                GlobalResource.mBookInfo.like = R.mipmap.icon_filled_like
            else
                GlobalResource.mBookInfo.like = R.mipmap.icon_empty_like
            updateLikeIcon()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setBookInfo() {
        binding.tvBookDate.text     = GlobalResource.mBookInfo.datetime.split("T")[0]
        binding.tvBookTitle.text    = GlobalResource.mBookInfo.title
        binding.tvBookFullInfo.text = GlobalResource.mBookInfo.contents
        binding.tvPrice.text        = GlobalResource.mBookInfo.price
        binding.tvPublisher.text    = GlobalResource.mBookInfo.publisher
    }

    private fun updateLikeIcon() {
        if (GlobalResource.mBookInfo.like == R.mipmap.icon_filled_like)
            binding.ivLike.setBackgroundResource(R.mipmap.icon_filled_like)
        else
            binding.ivLike.setBackgroundResource(R.mipmap.icon_empty_like)
    }

    private fun setBookImageView(imgUrl:String) {
        imgUrl?.let {
            Glide.with(requireContext())
                .load(imgUrl)
                .apply(
                    RequestOptions()
                        .transform(RoundedCorners(20))
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.ivBookImage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        Glide.with(binding.ivBookImage).clear(binding.ivBookImage)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}