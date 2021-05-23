package com.example.mypokedex.presentation.list


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypokedex.databinding.FragmentListBinding
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.inputmethod.EditorInfo

import android.app.SearchManager
import android.content.Context

import android.view.MenuInflater
import com.example.mypokedex.R


class ListFragment : Fragment(R.layout.fragment_list){


    private val viewModel: ListViewModel by viewModel()
    private val viewBinding: FragmentListBinding by viewBinding()
//   private var adapter = MainAdapter(
//       onItemClicked = ::openPokemonById
//   )
    private var adapter: MainAdapter? =null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        initViewModel()
        initRecyclerView()
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }
        viewModel.loadData()
           super.onViewCreated(view, savedInstanceState)
        }

    private fun initViewModel() {
        viewModel.viewState().observe(viewLifecycleOwner, ::showViewState)
    }
    private fun showViewState(state: PokemonListViewState) {
        when (state) {
            is PokemonListViewState.Loading -> {
                showProgress()
            }
            is PokemonListViewState.Error -> {
                showError(state.errorMessage)
            }
            is PokemonListViewState.Data -> {
                showData(state.items)
            }
        }
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

    private fun showProgress()= with(viewBinding) {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
        loadingStateLayout.root.isVisible = true
        errorStateLayout.root.isVisible = false
        recyclerView.isVisible = false
    }

    private fun showData(items: List<DisplayableItem>) = with(viewBinding) {
        loadingStateLayout.root.isVisible = false
        errorStateLayout.root.isVisible = false
        recyclerView.isVisible =true

        adapter?.submitList(items)
    }


    private fun showError(errorMessage: String)= with(viewBinding) {
        loadingStateLayout.root.isVisible = false
        errorStateLayout.root.isVisible = true
        recyclerView.isVisible = false

        errorStateLayout.errorMessageText.text = errorMessage
        errorStateLayout.retryButton.setOnClickListener {
            viewModel.loadData()
        }
    }
//    private fun openPokemonById(id: String) {
//        val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetails(id)
//        findNavController().navigate(action)
//    }


//    override fun onCreateOptionsMenu( menu: Menu,  inflater: MenuInflater) {
//
//setHasOptionsMenu(true)
//        inflater.inflate(R.menu.overflow_menu, menu)
//
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu.findItem(R.id.search).actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
//        searchView.queryHint = "Search..."
//        searchView.maxWidth = Int.MAX_VALUE
//        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                if (newText.isEmpty()) {
//                    Log.i(TAG, "onQueryTextChange: EMPTY")
//                    viewModel.filter.filter("")
//                } else {
//                    Log.i(TAG, "onQueryTextChange: search text is $newText")
//
//                   viewModel.filter.filter(newText)
//
//                }
//                return true
//            }
//        })
//       super.onCreateOptionsMenu(menu, inflater)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.search -> {
//                Toast.makeText(context,"is worked",Toast.LENGTH_SHORT).show()
//                return true
//            }
//            R.id.show ->{ Toast.makeText(context,"show",Toast.LENGTH_SHORT).show()
//return true
//            }
//
//        }
//
//      // return super.onOptionsItemSelected(item)
//return false
//    }



}






