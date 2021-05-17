package com.example.mypokedex.data

import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.domain.PokemonRepository
import com.example.mypokedex.domain.Result

class MockPokemonRepository: PokemonRepository {
    val items = mutableListOf<PokemonEntity>(
//        PokemonEntity("1", "bulbasaur", generateUrlFromId(1)),
//        PokemonEntity("2", "ivysaur", generateUrlFromId(2)),
//        PokemonEntity("3", "venusaur", generateUrlFromId(3), 1),
//        PokemonEntity("4", "charmander", generateUrlFromId(4),1),
//        PokemonEntity("5", "charmeleon", generateUrlFromId(5)),
//        PokemonEntity("6", "metal-powder", generateUrlFromId(234)),
//        PokemonEntity("7", "limber", generateUrlFromId(7)),
//        PokemonEntity("8", "quick-powder", generateUrlFromId(251)),
//        PokemonEntity("9", "imposter", generateUrlFromId(150)),
//        PokemonEntity("10", "ditto", generateUrlFromId(132))
    )
    override suspend fun getPokemonList(): Result<List<PokemonEntity>> = Result.Success(items)

    override suspend fun getPokemonById(id: String): Result<PokemonEntity> {
        val pokemon = items.find { it.id == id }

        return if (pokemon != null) {
            Result.Success(pokemon)
        } else {
            Result.Error(Exception("Not found"))
        }
    }


    fun generateUrlFromId(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}