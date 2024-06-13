package com.bhavanawagh.newsapp_mvvm_architecture.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class DefaultDispatcherProviderTest : DispatcherProvider{

    private val testDispatcher = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher get() =  testDispatcher
    override val io: CoroutineDispatcher get() = testDispatcher
    override val default: CoroutineDispatcher get() = testDispatcher

}