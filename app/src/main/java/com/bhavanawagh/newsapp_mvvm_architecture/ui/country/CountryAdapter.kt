package com.bhavanawagh.newsapp_mvvm_architecture.ui.country

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.NewsSourceItemLayoutBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class CountryAdapter (private val context: Context, private val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country, context: Context) {
            binding.button.text = country.name
            itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, TopHeadlineActivity::class.java)
                        .putExtra("EXTRAS_COUNTRY", country.id)
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
        holder.bind(countryList[position], context)


    override fun getItemCount(): Int = countryList.size

    fun addData(list: List<Country>) {
        countryList.addAll(list)
    }
}
