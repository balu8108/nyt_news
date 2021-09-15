package com.bala.nytnews.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bala.nytnews.R
import com.bala.nytnews.databinding.NewsItemLayoutBinding
import com.bala.nytnews.ui.main.data.NewsItem
import com.bumptech.glide.Glide

class NewsListAdapter(private val context: Context, val listener: (String) -> Unit) :
    PagingDataAdapter<NewsItem, NewsListAdapter.NewsItemViewHolder>(DiffCallBackNewsItems()) {

    inner class NewsItemViewHolder(val viewBinding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.newItemContainer.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> listener(it1.webUrl) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            NewsItemLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.viewBinding.apply {
            val lNewsItem = getItem(position)
            lNewsItem?.let {
                holder.viewBinding.apply {
                    Glide.with(context)
                        .load(lNewsItem.imageUrl)
                        .placeholder(R.drawable.news_item_place_holder)
                        .into(titleImage)
                    title.text = lNewsItem.title
                    snippet.text = lNewsItem.snippet
                    date.text = lNewsItem.date
                }
            }

        }
    }


    class DiffCallBackNewsItems : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) =
            oldItem.title == newItem.title
    }
}