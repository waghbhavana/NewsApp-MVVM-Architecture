package com.bhavanawagh.newsapp_mvvm_architecture.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinePaginationRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
open class NewsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val topHeadlinePaginationRepository: TopHeadlinePaginationRepository,
) : Worker(context, params) {

    override fun doWork(): Result {
        println("NewsWorker:  worker called")
        return try {
            println("NewsWorker:  worker called")
            CoroutineScope(Dispatchers.IO).launch {
                topHeadlinePaginationRepository.getTopHeadlinesOfflinePaging(AppConstants.EXTRAS_COUNTRY)

            }
            Result.success()
        } catch (e: Exception) {
            println("Exce : ${e.message}")
            Result.retry()
        }
    }
}