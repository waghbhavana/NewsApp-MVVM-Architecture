package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.room.Room
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.ApiKeyInterceptor
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.CacheInterceptor
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.ForceCacheInterceptor
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.AppDatabase
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.AppDatabaseService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.DatabaseService
import com.bhavanawagh.newsapp_mvvm_architecture.di.BaseUrl
import com.bhavanawagh.newsapp_mvvm_architecture.di.DatabaseName
import com.bhavanawagh.newsapp_mvvm_architecture.di.NetworkApiKey
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DefaultDispatcherProvider
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @NetworkApiKey
    @Provides
    fun provideNetworkApiKey(): String = "a5f22183ec404bd3b8f253cfb72671d7"
        //"a5f22183ec404bd3b8f253cfb72671d7"
        //"59268db289864425bc1ca98d11669422"

    @Provides
    fun provideApiKeyInterceptor(@NetworkApiKey networkApiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(networkApiKey)

    @Provides
    @Singleton
    fun provideCacheInterceptor(): CacheInterceptor = CacheInterceptor()

    @Provides
    @Singleton
    fun provideForceInterceptor(@ApplicationContext appContext: Context): ForceCacheInterceptor =
        ForceCacheInterceptor(appContext)


    @Provides
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        apiKeyInterceptor: ApiKeyInterceptor,
        cacheInterceptor: CacheInterceptor,
        forceCacheInterceptor: ForceCacheInterceptor,
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient()
        .newBuilder()
        .cache(Cache(File(appContext.cacheDir, "http-cache"), 10L * 1024L * 1024L))
        .addInterceptor(apiKeyInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .addInterceptor(forceCacheInterceptor)
        .addInterceptor(loggerInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient
    )
            : NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }


    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "news-database"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName
        databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

}