package com.example.mypokedex.presentation.list


import com.example.mypokedex.presentation.adapter.PokemonItem


sealed class PokemonListViewState {
    object Loading : PokemonListViewState()
    data class Error(val errorMessage: String) : PokemonListViewState()
    data class Data(val items: List<PokemonItem>) : PokemonListViewState()

}