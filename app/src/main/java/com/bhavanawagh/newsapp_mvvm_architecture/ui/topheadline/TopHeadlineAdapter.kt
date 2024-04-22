package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.TopHeadlineItemLayoutBinding
import com.bumptech.glide.Glide

class TopHeadlineAdapter(private val articleList: ArrayList<Article>) :
    RecyclerView.Adapter<TopHeadlineAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article){
            binding.textViewTitle.text= article.title
            binding.textViewDescription.text= article.description
            binding.textViewSource.text= article.source.name
            Glide.with(binding.imageViewBanner.context)
               // .load(article.urlToImage)
               // .into(binding.imageViewBanner)
            binding.textViewReadArticle.setOnClickListener {
                val builder = CustomTabsIntent.Builder().setShowTitle(true)
                val customTabsIntent = builder.build()
               customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= DataViewHolder (
        TopHeadlineItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))



    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articleList[position])


    override fun getItemCount(): Int = articleList.size

    fun addArticleData(articles: List<Article>){
        articleList.clear()
        articleList.addAll(articles)
    }
}