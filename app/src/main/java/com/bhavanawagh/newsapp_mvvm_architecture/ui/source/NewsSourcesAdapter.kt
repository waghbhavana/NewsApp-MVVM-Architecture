package com.bhavanawagh.newsapp_mvvm_architecture.ui.source


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhavanawagh.newsapp_mvvm_architecture.Source
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.NewsSourceItemLayoutBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class NewsSourcesAdapter(private val context: Context, private val sourceList: ArrayList<Source>) :
    RecyclerView.Adapter<NewsSourcesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Source, context: Context) {
            binding.button.text = source.name
            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, TopHeadlineActivity::class.java)
                        .putExtra("EXTRAS_SOURCE", source.id)
                )
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
        holder.bind(sourceList[position], context)


    override fun getItemCount(): Int = sourceList.size

    fun addData(list: List<Source>) {
        sourceList.addAll(list)
    }
}