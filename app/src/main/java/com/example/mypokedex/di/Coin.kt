package com.example.mypokedex.di

import com.example.mypokedex.DetailViewModel
import com.example.mypokedex.ListViewModel
import com.example.mypokedex.data.NetworkPokemonRepository
import com.example.mypokedex.data.network.PokedexApiService
import com.example.mypokedex.data.network.createPokedexApiService
import com.example.mypokedex.domain.PokemonDetails
import com.example.mypokedex.domain.PokemonRepository

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<PokedexApiService> { createPokedexApiService() }
    single<PokemonRepository> { NetworkPokemonRepository(get()) }

    viewModel { ListViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

private fun createPokedexApiService(): PokedexApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}