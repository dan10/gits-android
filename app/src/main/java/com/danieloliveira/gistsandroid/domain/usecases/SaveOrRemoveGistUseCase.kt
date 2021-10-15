package com.danieloliveira.gistsandroid.domain.usecases

import com.danieloliveira.gistsandroid.domain.base.IoDispatcher
import com.danieloliveira.gistsandroid.domain.base.UseCase
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.repository.GistRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveOrRemoveGistUseCase @Inject constructor(
    private val repository: GistRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : UseCase<GistModel, Unit>(coroutineDispatcher){

    override suspend fun execute(parameters: GistModel) {
        repository.saveOrRemoveGist(parameters)
    }
}