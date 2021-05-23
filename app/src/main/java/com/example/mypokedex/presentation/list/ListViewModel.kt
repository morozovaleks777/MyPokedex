package com.example.mypokedex.presentation.list

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.domain.PokemonRepository
import kotlinx.coroutines.launch
import com.example.mypokedex.domain.Result
import com.example.mypokedex.presentation.adapter.DisplayableItem
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.example.mypokedex.presentation.adapter.PokemonItem
import com.example.mypokedex.presentation.adapter.toItem

class ListViewModel(private val repository: PokemonRepository) : ViewModel() //, Filterable
 {
    //var adapter: MainAdapter? = null
    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

 private var itemsFilterList: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()


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


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence?): FilterResults {
//                val itemModal = emptyList<DisplayableItem>().toMutableList()
//                val charSearch = charSequence.toString()
//                if (charSearch.isNullOrEmpty()) {
//
//                    itemsFilterList = items
//                } else {
//                    val searchChr = charSequence.toString().toLowerCase(Locale.ROOT)
//                    // val itemModal = emptyList<DisplayableItem>().toMutableList()
//
//                    for (item in items) {
//                        if ((item as PokemonItem).name.contains(searchChr)) {
//                            itemModal.add(item)
//                        }
//
//                    }
//                    itemsFilterList = itemModal
//
//                }
//                val filterResults = FilterResults()
//                filterResults.values = itemsFilterList
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
//
//
//
//                adapter?.notifyDataSetChanged()
//
//            }
//override fun getFilter(): Filter {
//    return object : Filter() {
//        override fun performFiltering(charSequence: CharSequence): FilterResults {
//
//            val charString = charSequence.toString()
//
//            if (charString.isEmpty()) {
//                itemsFilterList = items
//            } else {
//
//                val filteredList =itemsFilterList
//                    .filter { (it as PokemonItem).name?.toLowerCase()?.contains(charString) }
//                    .toMutableList()
//
//                itemsFilterList = filteredList
//            }
//
//            val filterResults = FilterResults()
//            filterResults.values = itemsFilterList
//            return filterResults
//        }
//
//        @SuppressLint("NotifyDataSetChanged")
//        override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
//           adapter?.submitList(filterResults.values as MutableList<DisplayableItem>)
//           // adapter?.notifyDataSetChanged()
//        }
//    }
}













