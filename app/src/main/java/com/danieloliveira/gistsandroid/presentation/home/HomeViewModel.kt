package com.danieloliveira.gistsandroid.presentation.home

import androidx.lifecycle.*
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.danieloliveira.gistsandroid.domain.model.GistModel
import com.danieloliveira.gistsandroid.domain.usecases.GetGistsUseCase
import com.danieloliveira.gistsandroid.domain.usecases.SaveOrRemoveGistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.danieloliveira.gistsandroid.domain.base.Result
import com.danieloliveira.gistsandroid.domain.usecases.GetGistsFavoritesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow


class HomeViewModel @AssistedInject constructor(
    @Assisted isFavorites: Boolean,
    private val getGistsUseCase: GetGistsUseCase,
    private val getGistsFavoritesUseCase: GetGistsFavoritesUseCase,
    private val saveGistUseCase: SaveOrRemoveGistUseCase
) : ViewModel() {

    private val _saveState: MutableLiveData<Result<Pair<Int, GistModel>>> = MutableLiveData()
    val saveState: LiveData<Result<Pair<Int, GistModel>>> = _saveState

    init {
        if (!isFavorites)
            getGistsUseCase(GetGistsUseCase.Params(PAGING_CONFIG))
        else
            getGistsFavoritesUseCase(GetGistsFavoritesUseCase.Params(PAGING_CONFIG))
    }

    @ExperimentalCoroutinesApi
    val flow = getCorrectUseCase(isFavorites)
        .cachedIn(viewModelScope)

    @ExperimentalCoroutinesApi
    private fun getCorrectUseCase(favorites: Boolean): Flow<PagingData<GistModel>> {
        return if (favorites)
            getGistsFavoritesUseCase.flow
        else getGistsUseCase.flow
    }

    fun saveGist(gist: GistModel, position: Int) {
        viewModelScope.launch {
            when (val result = saveGistUseCase(gist)) {
                is Result.Success -> _saveState.value = Result.Success(
                    Pair(position, gist.copy(isFavorite = !gist.isFavorite))
                )
                is Result.Error -> _saveState.value = Result.Error(result.exception)
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(isFavorites: Boolean): HomeViewModel
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 10
        )

        fun provideFactory(
            assistedFactory: AssistedFactory,
            isFavorites: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(isFavorites) as T
            }
        }
    }
}