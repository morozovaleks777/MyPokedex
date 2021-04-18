package com.example.mypokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R

import com.example.mypokedex.data.network.PokemonApiFilter
import com.example.mypokedex.presentation.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel = MainViewModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
setSupportActionBar(findViewById(R.id.topAppBar))


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        viewModel.getPokemonList().observe(this) { pokemonList ->
            adapter.setPokemonList(pokemonList)
        }


        viewModel.loadData()

       // recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this,3)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem):Boolean {
//       return when (item.itemId){
//            R.id.action_search ->{
//                TODO()}
//            R.id.show_all_menu ->{
//
//                TODO()
//          }
//            R.id.show_by_generation ->{
//               TODO()
//
//            }
//
//            else -> super.onOptionsItemSelected(item)}
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.show_all_menu -> PokemonApiFilter.SHOW_ALL
                    R.id.show_by_generation -> PokemonApiFilter.SHOW_BY_GEN
                    else -> PokemonApiFilter.SHOW_SINGLE
                }

        return true
    }


}