package com.example.mypokedex

import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.domain.PokemonRepository

import kotlinx.coroutines.launch
import com.example.mypokedex.domain.Result
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.example.mypokedex.presentation.adapter.PokemonItem
import com.example.mypokedex.presentation.adapter.toItem
import java.util.*
import kotlin.collections.ArrayList

class ListViewModel(private val repository: PokemonRepository) : ViewModel(),Filterable
     {
var adapter: MainAdapter? =null
    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

    //lateinit var itemsFilterList: MutableList<DisplayableItem>
    var itemsFilterList = ArrayList<DisplayableItem>()

    init {
        itemsFilterList = items as ArrayList<DisplayableItem>
    }

    fun loadData() {
        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value = when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonItems = result.data.map { it.toItem() }
                    PokemonListViewState.Data(pokemonItems)
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSearch = charSequence.toString()
                if (charSearch.isBlank()) {

                    itemsFilterList = items as ArrayList<DisplayableItem>
                } else {
                    val searchChr = charSequence.toString().toLowerCase(Locale.ROOT)
                    val itemModal = emptyList<DisplayableItem>().toMutableList()

                    for (item in items) {
                        if ((item as PokemonItem).name.contains(searchChr)) {
                            itemModal.add(item)
                        }

                        itemsFilterList = itemModal as ArrayList<DisplayableItem>
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {

                items = filterResults?.values as ArrayList<DisplayableItem>
                 adapter?.notifyDataSetChanged()

            }


        }
    }


}






