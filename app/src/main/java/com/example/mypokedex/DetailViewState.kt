package com.example.mypokedex

sealed class DetailViewState {
    object Loading: DetailViewState()

    data class Data(
        val name: String,
        val imageUrl: String,
        val abilities: List<String>,
        val height: Int,
        val weight: Int,
        val stats: Map<String,String>
    ): DetailViewState()

    data class Error(val message: String): DetailViewState()
}