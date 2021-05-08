package com.example.mypokedex

sealed class DetailViewState {
    object Loading: DetailViewState()

    data class Data(
        val name: String,
        val imageUrl: String,
        val abilities: List<String>
    ): DetailViewState()

    data class Error(val message: String): DetailViewState()
}