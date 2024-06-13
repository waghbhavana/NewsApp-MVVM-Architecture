package com.bhavanawagh.newsapp_mvvm_architecture

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bhavanawagh.newsapp_mvvm_architecture.worker.NewsWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() , Configuration.Provider{

    @Inject
    lateinit var workerFactory : HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    }


    override fun onCreate() {
        super.onCreate()
        setUpWorker()
    }

    private fun setUpWorker(){
        // Create a retry policy with exponential backoff
        val retryPolicy = BackoffPolicy.EXPONENTIAL
        val retryInterval = 15L // Retry every 5 minutes
        val retryIntervalTimeUnit = TimeUnit.MINUTES

        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest.Builder(NewsWorker::class.java,1,TimeUnit.DAYS)
            .setBackoffCriteria(retryPolicy, retryInterval, retryIntervalTimeUnit)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}