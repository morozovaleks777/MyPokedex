package com.example.mypokedex


import android.os.Bundle
import android.view.*

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment(R.layout.fragment_list) {


    


    private val viewModel: ListViewModel by viewModel()
    private var adapter: MainAdapter? = null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        initRecyclerView()

setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.overflow_menu,menu)

            val menuItem= menu.findItem(R.id.searchView)
            val searchView=menuItem.actionView as SearchView
            searchView.maxWidth= Int.MAX_VALUE
            searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.filter.filter(query)

                   // return true
                    return false
                }

                override fun onQueryTextChange(filterString: String?): Boolean {
                    viewModel.filter.filter(filterString)
                   // return true
                    return false
                }

            })
       super.onCreateOptionsMenu(menu, inflater)


        }



        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.searchView ->{
                   viewModel.filter}

            }

            return false
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








