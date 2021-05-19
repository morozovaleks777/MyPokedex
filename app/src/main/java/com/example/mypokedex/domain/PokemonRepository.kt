package com.example.mypokedex.domain


interface PokemonRepository {
    suspend fun getPokemonList(): Result<List<PokemonEntity>>
    suspend fun getPokemonById(id: String): Result<PokemonEntity>
}