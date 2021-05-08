package com.example.mypokedex.data.network

import com.example.mypokedex.domain.PokemonEntity

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

enum class PokemonApiFilter(val value: String) { SHOW_SINGLE("single pokemon"),
    SHOW_BY_GEN("generation"), SHOW_ALL("all") }




fun createPokedexApiService(): PokedexApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}

interface PokedexApiService {
    /**
     * See for details: https://pokeapi.co/api/v2/pokemon
     */
    @GET("pokemon")
    fun fetchPokemonList(
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): Single<PokemonListResponse>

    /**
     * See for details: https://pokeapi.co/api/v2/pokemon/bulbasaur
     */
    @GET("pokemon/{name}")
    fun fetchPokemonInfo(@Path("name") name: String): Single<PokemonDetailedResponse>
    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String): List<PokemonEntity>

}

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonPartialResponse>,
)

data class PokemonPartialResponse(
    val name: String,
    val url: String
)

data class PokemonDetailedResponse(
    val id: String,
    val name: String,
    val abilities: List<PokemonAbilityData>
)

data class PokemonAbilityDetailsData(
    val name: String,
    val url: String
)

data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
    val is_hidden: Boolean,
    val slot: Int,
)


