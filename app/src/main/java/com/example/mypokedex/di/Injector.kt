package com.example.mypokedex.di
import com.example.mypokedex.data.NetworkPokemonRepository
import com.example.mypokedex.data.network.createPokedexApiService
import com.example.mypokedex.domain.PokemonRepository



object Injector {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    fun providePokemonRepository(): PokemonRepository = repository
}
