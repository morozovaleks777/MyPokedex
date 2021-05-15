package com.example.mypokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.domain.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.mypokedex.domain.Result

class DetailViewModel( private val repository: PokemonRepository): ViewModel() {

    private val viewStateLiveData = MutableLiveData<DetailViewState>()

    fun viewState(): LiveData<DetailViewState> = viewStateLiveData

    fun loadPokemonById(id: String) {
        viewStateLiveData.value = DetailViewState.Loading

        fun PokemonEntity.toDataViewState() = DetailViewState.Data(
            name = name,
            imageUrl = previewUrl,
            abilities = abilities
        )

        viewModelScope.launch {
            delay(2000)
            viewStateLiveData.value = when (val result = repository.getPokemonById(id)) {
                is Result.Success -> {
                    val responseData = result.data
                    responseData.toDataViewState()
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    createErrorViewState("Failed to load pokemon with id=$id")
                }
            }
        }
    }

    private fun createErrorViewState(message: String) = DetailViewState.Error(message)
}