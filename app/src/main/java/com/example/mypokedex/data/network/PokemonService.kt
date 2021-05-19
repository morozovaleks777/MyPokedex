package com.example.mypokedex.data.network

import com.example.mypokedex.domain.PokemonEntity
import retrofit2.Retrofit
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
       // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}

interface PokedexApiService {
    /**
     * See for details: https://pokeapi.co/api/v2/pokemon
     */
    @GET("pokemon")
   suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    /**
     * See for details: https://pokeapi.co/api/v2/pokemon/bulbasaur
     */
    @GET("pokemon/{name}")
   suspend fun fetchPokemonDetail(@Path("name") name: String): PokemonDetailedResponse
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
    val abilities: List<PokemonAbilityData>,
    val height:Int,
    val weight:Int,
     val stats:List<PokemonStatsData>,
      val types: List<PokemonTypesData>,

)
// Stats
data class PokemonStatsData(
    val stat: PokemonStatsDetailData,
    val base_stat: String
)

data class PokemonStatsDetailData(
    val name: String
)

// Abilities
data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
)

data class PokemonAbilityDetailsData(
    val name: String
)

data class PokemonTypesData(
    val slot: Int,
    val type: PokemonTypesDetailsData,
)

data class PokemonTypesDetailsData(
    val name: String,
    val url: String,
)

