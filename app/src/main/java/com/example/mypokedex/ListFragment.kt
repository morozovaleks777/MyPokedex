package com.example.mypokedex


import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.example.mypokedex.presentation.adapter.PokemonItem
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ListFragment : Fragment(R.layout.fragment_list) {


    lateinit var types: DetailViewState.Data
    private val viewModel: ListViewModel by viewModel()
    private var adapter: MainAdapter? = null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        initRecyclerView()



        viewModel.viewState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonListViewState.Loading -> {
                    showProgress()
                }
                is PokemonListViewState.Error -> {
                    showError(state.message)
                }
                is PokemonListViewState.Data -> {
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

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter

    }

    private fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
    }

    private fun showData(items: List<DisplayableItem>) {
        adapter?.setPokemonList(items)

    }

    }




    private fun showError(errorMessage: String) {

    }








