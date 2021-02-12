package com.jwhh.notekeeper.presentation.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.presentation.entities.NewsPublisher
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    var articles = mutableListOf<NewsPublisher>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsPublisherItem: NewsPublisher) {
            with(itemView) {
                heading.text = newsPublisherItem.name
                textViewNewUrl.text = newsPublisherItem.url
                textViewNewDescription.text = newsPublisherItem.description
            }
        }
    }

    fun updateList(list: List<NewsPublisher>) {
        if (list.isNotEmpty()) {
            articles.clear()
            articles.addAll(list)
            notifyDataSetChanged()
        }
    }
}