package com.example.mypokedex.domain

import io.reactivex.Single



interface PokemonRepository {
   suspend fun getPokemonList(): Result<List<PokemonEntity>>
   suspend fun getPokemonById(id: String): Result<PokemonEntity>
}