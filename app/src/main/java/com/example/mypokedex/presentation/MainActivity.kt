package com.example.mypokedex.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mypokedex.presentation.ero.EroActivity
import com.example.mypokedex.presentation.ero.PokedexEro
import com.example.mypokedex.R
import com.example.mypokedex.presentation.detail.DetailFragment
import com.example.mypokedex.presentation.list.ListFragment

class MainActivity : AppCompatActivity(), Navigation, Navigation2, Navigation3 {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ListFragment())
            .commit()
    }

    override fun openPokemonDetails(id: String) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.animator.card_flip_right_enter,
                R.animator.card_flip_right_exit,
                R.animator.card_flip_left_enter,
                R.animator.card_flip_left_exit
            )
            .replace(android.R.id.content, DetailFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun openPokedexEro() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.animator.card_flip_right_enter,
                R.animator.card_flip_right_exit,
                R.animator.card_flip_left_enter,
                R.animator.card_flip_left_exit
            )
            .replace(R.id.cardView, PokedexEro())
            .addToBackStack(null)
            .commit()
    }

    override fun openPokemonList() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.animator.card_flip_right_enter,
                R.animator.card_flip_right_exit,
                R.animator.card_flip_left_enter,
                R.animator.card_flip_left_exit
            )
            .replace(R.id.cardView, ListFragment())
            .addToBackStack(null)
            .commit()
    }

    fun click(view: View) {
        onBackPressed()
    }

    fun click1(view: View) {

        val intent = Intent(this, EroActivity::class.java)
        startActivity(intent)
    }
}

interface Navigation {
    fun openPokemonDetails(id: String)
}
interface Navigation2 {
    fun openPokemonList()

}
interface Navigation3 {
    fun openPokedexEro()
}









