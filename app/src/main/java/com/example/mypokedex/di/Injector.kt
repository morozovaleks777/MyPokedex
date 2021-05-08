package com.example.mypokedex.di

import com.example.mypokedex.DetailViewModel
import com.example.mypokedex.data.NetworkPokemonRepository
import com.example.mypokedex.data.network.createPokedexApiService
import com.example.mypokedex.domain.PokemonRepository
import com.example.mypokedex.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Injector {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    fun providePokemonRepository(): PokemonRepository = repository
}
