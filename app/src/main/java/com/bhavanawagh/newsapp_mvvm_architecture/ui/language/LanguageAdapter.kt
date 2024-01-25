package com.bhavanawagh.newsapp_mvvm_architecture.ui.language

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.NewsSourceItemLayoutBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

class LanguageAdapter (private val context: Context, private val languageList: ArrayList<Language>) :
    RecyclerView.Adapter<LanguageAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(language: Language, context: Context) {
            binding.button.text = language.name
            itemView.setOnClickListener {
//                context.startActivity(
//                    Intent(context, TopHeadlineActivity::class.java)
//                        .putExtra("EXTRAS_LANGUAGE", language.id)
//                )
                TopHeadlineActivity.getLanguageForNewsList(context, language.id,"LANGUAGE")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        NewsSourceItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(languageList[position], context)


    override fun getItemCount(): Int = languageList.size

    fun addData(list: List<Language>) {
        languageList.addAll(list)
    }
}
