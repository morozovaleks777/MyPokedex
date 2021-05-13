package com.example.mypokedex

import android.graphics.drawable.ColorDrawable
import android.icu.util.TimeUnit
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.presentation.Navigation
import com.example.mypokedex.presentation.Navigation2
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val viewModel = DetailViewModel()
    private var adapter: MainAdapter? = null
    private val navigation: Navigation2? by lazy { (activity as? Navigation2) }


    companion object {
        private const val PARAM_POKEMON_ID = "Pockemon_Id"

        fun newInstance(id: String): Fragment = DetailFragment().apply {
            arguments = bundleOf(
                PARAM_POKEMON_ID to id
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
postponeEnterTransition(500, java.util.concurrent.TimeUnit.MILLISECONDS)
        backToList()

        val id = arguments?.getString(PARAM_POKEMON_ID)

        if (id != null) {
            loadPokemonData(view, id)

        } else {
            Log.d("TAG, ","Error, pokemon with id=$id not found")
        }
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }

    }


    private fun loadPokemonData(view: View, id: String) {
        viewModel.loadPokemonById(id)

        val progressView = view.findViewById<ProgressBar>(R.id.progress)
        val contentView = view.findViewById<View>(R.id.content_group)
       val errorView = view.findViewById<TextView>(R.id.error_message_text)

        viewModel.viewState().observe(viewLifecycleOwner) { viewState ->
            when(viewState) {
                DetailViewState.Loading -> {
                    progressView.isVisible = true
                    contentView.isVisible = false
                    errorView.isVisible = false
                }
                is DetailViewState.Data -> {
                    progressView.isVisible = false
                    contentView.isVisible = true
                    errorView.isVisible = false

                    showDataState(view = view, state = viewState)
                }
                is DetailViewState.Error -> {
                    progressView.isVisible = false
                    contentView.isVisible = false
                    errorView.isVisible = true
                }
            }
        }
    }
    private fun backToList() {
       adapter = MainAdapter {
            navigation?.openPokemonList()

        }





    }

    private fun showDataState(view: View, state: DetailViewState.Data) {
        val nameTextView = view.findViewById<TextView>(R.id.name)
        val imagePreview = view.findViewById<ImageView>(R.id.image)
        val abilitiesTextView = view.findViewById<TextView>(R.id.error_message_text)

        nameTextView.text = state.name

        Picasso.get().load(state.imageUrl).into(imagePreview)

        abilitiesTextView.text = state.abilities.joinToString(separator = ",") { it }
    }
}

