package com.example.mypokedex.domain

import androidx.core.net.toUri

data class PokemonListResponse(val count : Int, val results : List<PokemonPartialInfo>)

data class PokemonPartialInfo(val name : String, val url : String)

val PokemonPartialInfo.id: String
    get() = url.toUri().lastPathSegment ?: ""

val PokemonPartialInfo.imageUrl: String
    get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"


data class PokemonInfoResponse(
    val id: String,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>
) {
    data class Ability(
        val ability: AbilityPartialInfo,
        val slot: Int
    )

    data class AbilityPartialInfo(
        val name: String,
        val url: String
    )
}