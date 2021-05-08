package com.example.mypokedex

import android.icu.util.TimeUnit
import android.os.Bundle


import android.view.*

import android.view.View.inflate
import android.widget.*
import androidx.core.app.SharedElementCallback
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.mypokedex.databinding.ActivityMainBinding.inflate
import com.example.mypokedex.presentation.MainActivity
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter



class ListFragment : Fragment(R.layout.fragment_list) {


    private val viewModel = ListViewModel()
    private var adapter: MainAdapter? = null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        initRecyclerView()



        viewModel.viewState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonListViewState.LoadingState -> {
                    showProgress()
                }
                is PokemonListViewState.ErrorState -> {
                    showError(state.errorMessage)
                }
                is PokemonListViewState.ContentState -> {

                    showData(state.items)

                }
            }

        }

        viewModel.loadData()
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }


    }


    private fun initRecyclerView() {
        adapter = MainAdapter(

            onItemClicked = { id ->
                navigation?.openPokemonDetails(id)
            }
        )

        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context,3)
        recyclerView.adapter = adapter

    }

    private fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
    }

    private fun showData(items: List<DisplayableItem>) {
        adapter?.setPokemonList(items)
    }

    private fun showError(errorMessage: String) {

    }





//    override fun onOptionsItemSelected(item: MenuItem):Boolean {
//        return when (item.itemId){
//            R.id.action_search ->{
//                val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
//                recyclerView?.layoutManager = GridLayoutManager(context,3)
//                recyclerView?.adapter = adapter
//    var  id= R.id.editText
//                recyclerView?.scrollToPosition(id)
//            true}
//            R.id.show_all_menu ->{
//                TODO()
//
//            }
//            R.id.show_by_generation ->{
//                TODO()
//
//            }
//
//            else -> super.onOptionsItemSelected(item)}
//    }

}


