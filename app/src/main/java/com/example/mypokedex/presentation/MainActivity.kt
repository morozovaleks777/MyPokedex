package com.example.mypokedex.presentation


import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.mypokedex.presentation.detail.DetailFragment
import com.example.mypokedex.presentation.list.ListFragment
import com.example.mypokedex.R
import com.example.mypokedex.presentation.adapter.MainAdapter


class MainActivity : AppCompatActivity(), Navigation, Navigation2 {
    var mainAdapter: MainAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            // .setReorderingAllowed(false)
            //.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
            //  R.animator.slide_in_left, R.animator.slide_in_right)
            .replace(android.R.id.content, ListFragment())
            .commit()


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun openPokemonDetails(id: String) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
//                   R.animator.slide_in_left, R.animator.slide_in_right,
//                   R.animator.card_flip_right_enter, R.animator.card_flip_right_exit)
                R.animator.card_flip_right_enter,
                R.animator.card_flip_right_exit,
                R.animator.card_flip_left_enter,
                R.animator.card_flip_left_exit
            )

            .replace(android.R.id.content, DetailFragment.newInstance(id))

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


}

interface Navigation {
    fun openPokemonDetails(id: String)
}
interface Navigation2 {
    fun openPokemonList()

}









