package com.example.mypokedex

import com.example.mypokedex.presentation.adapter.PokemonItem


sealed class PokemonListViewState {
    object Loading: PokemonListViewState()
    data class Error(val message: String): PokemonListViewState()
    data class Data(val items: List<PokemonItem>): PokemonListViewState()
}