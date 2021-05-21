package com.example.mypokedex.presentation.adapter


import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter

import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.DetailViewState
import com.example.mypokedex.ListViewModel
import com.example.mypokedex.R
import com.example.mypokedex.databinding.HeaderItemBinding
import com.example.mypokedex.databinding.MainItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class MainAdapter(
    private val onItemClicked: (id: String) -> Unit
) : ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(PokemonItemDiffCallback) {

  //  private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()



//    fun setPokemonList(pokemons: List<DisplayableItem>) {
//        items.clear()
// items.addAll(pokemons )
//
//
//        notifyDataSetChanged()
//
//    }

    private object PokemonItemDiffCallback : DiffUtil.ItemCallback<DisplayableItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean = false
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<CardView>(R.id.cardView)
            .setCardBackgroundColor(setRandomCardColor(holder.itemView.context))

        //holder.itemView.setBackgroundColor(setRandomCardColor(holder.itemView.context))


        when (val itemToShow = getItem(position) ){
            is PokemonItem -> {
                (holder as PokemonViewHolder).bind(itemToShow)
            }
            is HeaderItem -> {
                (holder as HeaderViewHolder).bind(itemToShow)
            }
        }
    }

    private fun setRandomCardColor(context: Context): Int {
        val coloredList: IntArray = context.resources.getIntArray(R.array.colors)
        return coloredList[Random.nextInt(1, coloredList.size)]
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    //override fun getItemCount(): Int = items.size

    class PokemonViewHolder(view: View, val onItemClicked: (id: String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val binding = MainItemBinding.bind(itemView)

        fun bind(item: PokemonItem) = with(binding) {
            name.text = item.name

            if (item.useRedColor) {
                name.setTextColor(Color.RED)
            } else {
                name.setTextColor(Color.BLACK)
            }

            Picasso.get().load(item.image).into(imagePreview)

            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HeaderItemBinding.bind(itemView)

        fun bind(item: HeaderItem) = with(binding) {
            headerText.text = item.text
        }
    }


}