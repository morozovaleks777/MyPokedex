package com.example.mypokedex.presentation


import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mypokedex.EronActivity
import com.example.mypokedex.PokedexEro

import com.example.mypokedex.presentation.detail.DetailFragment
import com.example.mypokedex.presentation.list.ListFragment
import com.example.mypokedex.R
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.example.mypokedex.presentation.detail.DetailFragment.Companion.newInstance






class MainActivity : AppCompatActivity(), Navigation, Navigation2,Navigation3 {
    var mainAdapter: MainAdapter? = null


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
override fun openPokedexEro(){
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
    fun click1(view: View){

        val intent = Intent(this, EronActivity::class.java)
        startActivity(intent)
    }
    fun floatClick(view: View){
        val url = "https://www.youtube.com/watch?v=rFI4JfbgOSY" // your URL here
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare() // might take long! (for buffering, etc)
            start()
        }
    }
}

interface Navigation {
    fun openPokemonDetails(id: String)
}
interface Navigation2 {
    fun openPokemonList()

}
interface Navigation3{
    fun openPokedexEro()
}









