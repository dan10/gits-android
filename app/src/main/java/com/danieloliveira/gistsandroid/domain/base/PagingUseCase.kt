package com.danieloliveira.gistsandroid.domain.base

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

abstract class PagingUseCase<in P: PagingUseCase.Parameters, R : Any> {

    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    interface Parameters {
        val pagingConfig: PagingConfig
    }

    @ExperimentalCoroutinesApi
    val flow: Flow<PagingData<R>> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()

    operator fun invoke(parameters: P) {
        paramState.tryEmit(parameters)
    }

    protected abstract fun createObservable(parameters: P): Flow<PagingData<R>>
}