package com.example.mypokedex.presentation

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.transition.Slide
import com.example.mypokedex.DetailFragment
import com.example.mypokedex.ListFragment
import com.example.mypokedex.R
import com.example.mypokedex.data.network.PokemonApiFilter


class MainActivity : AppCompatActivity(), Navigation,Navigation2 {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            supportFragmentManager.beginTransaction()
               .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
                   R.animator.slide_in_left, R.animator.slide_in_right
               )
                .replace(android.R.id.content, ListFragment())
                .commit()


        }

       override fun openPokemonDetails(id: String) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
                    R.animator.slide_in_left, R.animator.card_flip_right_exit)
                .replace(android.R.id.content, DetailFragment.newInstance(id))

                .addToBackStack(null)

                .commit()


        }
    override fun openPokemonList(){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
                R.animator.slide_in_left, R.animator.slide_in_right
            )
            .replace(R.id.cardView, ListFragment())
            .commit()

    }
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.overflow_menu,menu)
            return super.onCreateOptionsMenu(menu)
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.show_all_menu -> PokemonApiFilter.SHOW_ALL
                R.id.show_by_generation -> PokemonApiFilter.SHOW_BY_GEN
                else -> PokemonApiFilter.SHOW_SINGLE
            }

            return true
        }


    }




interface Navigation {
        fun openPokemonDetails(id: String)}
interface Navigation2{
    fun openPokemonList()
}









