package com.example.mypokedex.presentation.adapter

interface DisplayableItem

data class PokemonItem(
    val id: String,
    val name: String,
    val image: String,
    val useRedColor: Boolean = false,
): DisplayableItem

data class HeaderItem(
    val text: String
): DisplayableItem