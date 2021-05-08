package com.example.mypokedex.presentation


import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.mypokedex.DetailFragment
import com.example.mypokedex.ListFragment
import com.example.mypokedex.R
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import java.util.Locale.filter

class MainActivity : AppCompatActivity(), Navigation,Navigation2 {
var mainAdapter:MainAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
                   R.animator.slide_in_left, R.animator.slide_in_right)
                .replace(android.R.id.content, ListFragment())

                .commit()


        }

       override fun openPokemonDetails(id: String) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right,
                    R.animator.card_flip_right_enter, R.animator.card_flip_right_exit)
                .replace(android.R.id.content, DetailFragment.newInstance(id))

                .addToBackStack(null)

                .commit()



        }

    override fun openPokemonList(){
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(R.animator.card_flip_right_enter,
                R.animator.card_flip_right_exit,
                R.animator.card_flip_left_enter, R.animator.card_flip_left_exit
            )
            .replace(R.id.cardView, ListFragment())

            .commit()

    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.overflow_menu,menu)

            val menuItem=menu!!.findItem(R.id.searchView)
            val searchView=menuItem.actionView as SearchView
            searchView.maxWidth= Int.MAX_VALUE
            searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                 //   mainAdapter?.filter?.filter(query)
                   // return true
                    return false
                }

                override fun onQueryTextChange(filterString: String?): Boolean {
            mainAdapter?.filter?.filter(filterString)
                   // return true
                    return false
                }

            })
            return super.onCreateOptionsMenu(menu)
        }

//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//            when (item.itemId) {
//                R.id.show_all_menu -> PokemonApiFilter.SHOW_ALL
//                R.id.show_by_generation -> PokemonApiFilter.SHOW_BY_GEN
//                else -> PokemonApiFilter.SHOW_SINGLE
//            }
//
//            return true
//        }

    fun click(view: View) {
        onBackPressed()

    }


}




interface Navigation {
        fun openPokemonDetails(id: String)}
interface Navigation2{
    fun openPokemonList()
}









