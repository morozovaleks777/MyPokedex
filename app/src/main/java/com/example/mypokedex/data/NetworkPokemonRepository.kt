package com.example.mypokedex.data

import com.example.mypokedex.data.network.PokedexApiService
import com.example.mypokedex.data.network.PokemonDetailedResponse

import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.domain.PokemonRepository
import com.example.mypokedex.domain.Result
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkPokemonRepository(
    val api: PokedexApiService
) : PokemonRepository {
    override suspend fun getPokemonList(): Result<List<PokemonEntity>> =
        withContext(Dispatchers.IO) {
            try {
                val ids = api.fetchPokemonList().results.map { it.name }
                val pokemonListWithDetails = ids.map { id ->
                    api.fetchPokemonDetail(id).toEntity()
                }
                Result.Success(pokemonListWithDetails)
            } catch (th: Exception) {
                Result.Error(th)
            }
        }

    override suspend fun getPokemonById(id: String): Result<PokemonEntity> =
        withContext(Dispatchers.IO) {

            try {

                val entity = api.fetchPokemonDetail(id).toEntity()

                Result.Success(entity)
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    private fun PokemonDetailedResponse.toEntity() =

        PokemonEntity(

            id = id,
            name = name,
            previewUrl = generateUrlFromId(id),
            abilities = abilities.map { it.ability.name },
            height = height,
            weight = weight,
            stats = stats.map { it.stat.name to it.base_stat }.toMap(),
            types = types.map { it.type.name }
        )


    fun generateUrlFromId(id: String): String =
        "https://pokeres.bastionbot.org/images/pokemon/$id.png"
    // "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}