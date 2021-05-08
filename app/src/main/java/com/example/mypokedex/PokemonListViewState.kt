package com.example.mypokedex

import com.example.mypokedex.presentation.adapter.PokemonItem


sealed class PokemonListViewState {
    object LoadingState: PokemonListViewState()
    data class ErrorState(val errorMessage: String): PokemonListViewState()
    data class ContentState(val items: List<PokemonItem>): PokemonListViewState()
}