package com.example.mypokedex.presentation.adapter

import com.example.mypokedex.domain.PokemonEntity

interface DisplayableItem

data class PokemonItem(

    val id: String,
    val name: String,
    val image: String,
    val useRedColor: Boolean = false,

    ) : DisplayableItem

data class HeaderItem(
    val text: String
) : DisplayableItem

fun PokemonEntity.toItem(): PokemonItem =
    PokemonItem(id, name, previewUrl)