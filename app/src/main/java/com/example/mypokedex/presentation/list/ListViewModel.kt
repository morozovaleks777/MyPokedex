package com.example.mypokedex.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.domain.PokemonRepository
import com.example.mypokedex.domain.Result
import com.example.mypokedex.presentation.adapter.PokemonItem
import com.example.mypokedex.presentation.adapter.toItem
import kotlinx.coroutines.launch

class ListViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()

    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.Loading
        loadDataWithFilter()
    }

    fun filterBy(filter: String?) {
        loadDataWithFilter(filter)
    }

    private fun loadDataWithFilter(filter: String? = null) {
        viewModelScope.launch {
            viewStateLiveData.value = when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonItems = result.data.map { it.toItem() }
                    val withFilter: List<PokemonItem>
                    if (filter != null) {
                        withFilter = pokemonItems.filter { it.name.contains(filter) }
                        when (withFilter.isNotEmpty()) {
                            true -> PokemonListViewState.Data(withFilter)
                            false -> {
                                PokemonListViewState.Error("ups")
                            }
                        }
                    } else {
                        PokemonListViewState.Data(pokemonItems)
                    }
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }
}














