package com.danieloliveira.gistsandroid.domain.usecases

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danieloliveira.gistsandroid.domain.base.PagingUseCase
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.repository.GistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGistsFavoritesUseCase @Inject constructor(
    private val repository: GistRepository
): PagingUseCase<GetGistsFavoritesUseCase.Params, GistModel>() {

    data class Params(
        override val pagingConfig: PagingConfig
    ): Parameters

    override fun createObservable(parameters: Params): Flow<PagingData<GistModel>> {
        return repository.getFavoriteGists(parameters.pagingConfig)
    }
}