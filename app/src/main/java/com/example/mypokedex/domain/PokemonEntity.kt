package com.example.mypokedex.domain

data class PokemonEntity(
    val id: String,
    val name: String,
    val previewUrl: String,
    val generation: Int = 0,
    val abilities: List<String> = emptyList(),
    val height: Int,
    val weight: Int,
    val stats: Map<String, String> = emptyMap(),
    val types: List<String>,
    val hp: Int = (minHp..maxHp).random()
) {
    companion object {
        const val minHp = 100
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpeed = 300
        const val minExp = 10
        const val maxExp = 1000

    }
}