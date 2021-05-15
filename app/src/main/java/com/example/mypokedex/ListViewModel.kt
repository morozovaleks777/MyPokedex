package com.example.mypokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.domain.PokemonRepository

import kotlinx.coroutines.launch
import com.example.mypokedex.domain.Result
import com.example.mypokedex.presentation.adapter.toItem

class ListViewModel(private val repository: PokemonRepository) : ViewModel() {





        private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
        fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value =  when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonItems = result.data.map { it.toItem() }
                    PokemonListViewState.Data(pokemonItems)
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }

        }

