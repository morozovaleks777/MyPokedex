package com.example.mypokedex.presentation.list

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypokedex.R
import com.example.mypokedex.databinding.FragmentListBinding
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged


class ListFragment : Fragment(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModel()
    private val viewBinding: FragmentListBinding by viewBinding()

    private var adapter: MainAdapter? = null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition(1000, java.util.concurrent.TimeUnit.MILLISECONDS)
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }
        viewModel.loadData()

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "App title"
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)

        val searchEditText: EditText = view.findViewById(R.id.searchEditText)

        searchEditText.doOnTextChanged { text, start, before, count ->
            viewModel.filterBy(text.toString())
        }
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

    private fun showProgress() = with(viewBinding) {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
        loadingStateLayout.root.isVisible = true
        errorStateLayout.root.isVisible = false
        recyclerView.isVisible = false
    }

    private fun showData(items: List<DisplayableItem>) = with(viewBinding) {
        loadingStateLayout.root.isVisible = false
        errorStateLayout.root.isVisible = false
        recyclerView.isVisible = true

        adapter?.submitList(items)
    }

    private fun showError(errorMessage: String) = with(viewBinding) {
        loadingStateLayout.root.isVisible = false
        errorStateLayout.root.isVisible = true
        recyclerView.isVisible = false

        errorStateLayout.errorMessageText.text = errorMessage
        errorStateLayout.retryButton.setOnClickListener {
            viewModel.loadData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)

        menu?.findItem(R.id.show)?.setOnMenuItemClickListener {
            Toast.makeText(context, "clicked on Show", Toast.LENGTH_LONG).show()
            true
        }
    }

}






