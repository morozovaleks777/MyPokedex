package com.example.mypokedex.presentation.adapter


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*


private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class MainAdapter(
    private val onItemClicked: (id: String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable {

    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()
lateinit var itemsFilterList: MutableList<DisplayableItem>
    fun setPokemonList(pokemons: List<DisplayableItem>) {
        items.clear()
       items.addAll(pokemons)

      //this.itemsFilterList=items

        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_item, parent, false)
                PokemonViewHolder(view, onItemClicked)


            }

            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val itemToShow = items[position]) {
            is PokemonItem  -> {
                (holder as PokemonViewHolder).bind(itemToShow)
            }
            is HeaderItem -> {
                (holder as HeaderViewHolder).bind(itemToShow)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PokemonItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    override fun getItemCount(): Int =items.size

    class PokemonViewHolder(view: View, val onItemClicked: (id: String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<TextView>(R.id.name)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)

        fun bind(item: PokemonItem) {
            textView.text = item.name

            if (item.useRedColor) {
                textView.setTextColor(Color.RED)
            } else {
                textView.setTextColor(Color.BLACK)
            }

            Picasso.get().load(item.image).into(imagePreview, object : Callback {
                override fun onSuccess() {
                    Log.d("", "Loaded image")
                }

                override fun onError(e: Exception?) {
                    Log.d("", "Loaded image", e)
                }
            })

            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bannerName = itemView.findViewById<TextView>(R.id.headerText)

        fun bind(item: HeaderItem) {
            bannerName.text = item.text
        }
    }



    override fun getFilter(): Filter {
       return object :Filter(){
           override fun performFiltering(charSequence: CharSequence?): FilterResults {
              val charSearch=charSequence.toString()
               if (charSearch.isEmpty() ) {

                   //filterResults.count = itemsFilterList.size
                   //filterResults = itemsFilterList
                   //itemsFilterList=items

                   itemsFilterList=items
               } else {
                   val searchChr = charSequence.toString().toLowerCase(Locale.ROOT)
                   val itemModal = emptyList<DisplayableItem>().toMutableList()

                   for(item in items){
                       if (  (item as PokemonItem).name.contains(searchChr)) {
                           itemModal.add(item)
                       }

                       itemsFilterList=itemModal
               }
           }
              val filterResults = FilterResults()
               filterResults.values = itemsFilterList
               return filterResults
           }

           @Suppress("UNCHECKED_CAST")
           override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {

               items = filterResults?.values as MutableList<DisplayableItem>
               notifyDataSetChanged()
           }


       }
    }

}