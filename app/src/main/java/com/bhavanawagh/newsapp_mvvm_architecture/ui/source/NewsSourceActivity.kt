package com.bhavanawagh.newsapp_mvvm_architecture.ui.source

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Source
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityNewsSourceBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourceActivity : AppCompatActivity() {

    @Inject
    lateinit var newsSourcesViewModel: NewsSourcesViewModel

    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter

    lateinit var binding: ActivityNewsSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding=ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        newsSourcesViewModel.fetchNewsSources()
        setUpObserver()

    }

    private fun setUpUI(){
        val recyclerView= binding.recyclerView2
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter=newsSourcesAdapter
    }
    private fun setUpObserver(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsSourcesViewModel.uiState.collect{
                    when(it){
                        is UiState.Loading->{
                            binding.progressBar2.visibility=View.VISIBLE
                            binding.recyclerView2.visibility=View.GONE
                        }
                        is UiState.Success->{
                            binding.progressBar2.visibility=View.GONE
                            renderList(it.data)
                            binding.recyclerView2.visibility=View.VISIBLE
                        }
                        is UiState.Error->{
                            binding.progressBar2.visibility=View.GONE
                        }

                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<Source>){
        newsSourcesAdapter.addData(sourceList)
        newsSourcesAdapter.notifyDataSetChanged()
    }
    private fun injectDependencies(){
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this@NewsSourceActivity)

    }


}