package com.example.mypokedex.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mypokedex.ARG_OBJECT
import com.example.mypokedex.PokedexEro
import com.example.mypokedex.R


class NumberAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    val listOfPokemons = arrayListOf(
        R.drawable.lugia_pokedex_1,
        R.drawable.ero_pokemon_2,
        R.drawable.persian_3,
        R.drawable.lopunn_4,
        R.drawable.gardevoir_5,
        R.drawable.delphox_6,
        R.drawable.gardevoir_7,
        R.drawable.mightyena_8,
        R.drawable.pikachu_9,
        R.drawable.taranimart_blaziken_10,
        R.drawable.vader_san_furry_11,
        R.drawable.wyntersun_12,
        R.drawable.pinktaco_13,
        R.drawable.yuuyatails_lugia_13
    )

    override fun getItemCount(): Int = listOfPokemons.size + 1

    override fun createFragment(position: Int): Fragment {
        val fragment = PokedexEro()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}