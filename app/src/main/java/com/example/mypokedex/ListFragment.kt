package com.example.mypokedex


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypokedex.databinding.FragmentListBinding
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel



class ListFragment : Fragment(R.layout.fragment_list) {


    private val viewModel: ListViewModel by viewModel()
    private val viewBinding: FragmentListBinding by viewBinding()
//    private var adapter = MainAdapter(
//        onItemClicked = ::openPokemonById
//    )
    private var adapter: MainAdapter? =null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        viewModel.loadData()

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        initViewModel()
        initRecyclerView()
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }

            super.onViewCreated(view, savedInstanceState)
        }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
//
//        inflater.inflate(R.menu.overflow_menu, menu)
//
//        val menuItem = menu.findItem(R.id.searchView)
//        menuItem.setIcon(R.drawable.ic_baseline_search_24)
//        val searchView = menuItem.actionView as SearchView
//        searchView.maxWidth = Int.MAX_VALUE
//        searchView.queryHint = "Search People"
//
//        searchView.isIconified = false
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                Toast.makeText(context, "2222222", Toast.LENGTH_SHORT).show()
//                //viewModel.filter.filter(query)
//
//                return true
//                // return false
//            }
//
//            override fun onQueryTextChange(filterString: String?): Boolean {
//                Toast.makeText(context, "11111111", Toast.LENGTH_SHORT).show()
//                viewModel.filter.filter(filterString)
//                // return true
//                return false
//            }
//
//        })
//
//       return super.onCreateOptionsMenu(menu, inflater)
//
//
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//            R.id.searchView -> {
//                viewModel.filter
//            }
//
//        }
//
//        return true
//    }
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

}






