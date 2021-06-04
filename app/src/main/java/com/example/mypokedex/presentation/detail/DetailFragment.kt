package com.example.mypokedex.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.mypokedex.R
import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.presentation.Navigation2
import com.example.mypokedex.presentation.Navigation3
import com.example.mypokedex.presentation.adapter.MainAdapter
import com.skydoves.progressview.ProgressView
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.RelativeLayout
import by.kirich1409.viewbindingdelegate.viewBinding


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val viewModel: DetailViewModel by viewModel()
    private var adapter: MainAdapter? = null
    private val navigation: Navigation2? by lazy { (activity as? Navigation2) }
    private val navigation2: Navigation3? by lazy { (activity as? Navigation3) }
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
        goToPokedexEro()
        val id = arguments?.getString(PARAM_POKEMON_ID)
        if (id != null) {
            loadPokemonData(view, id)
        } else {
            Log.d("TAG, ", "Error, pokemon with id=$id not found")
        }
        (view.parent as? View)?.doOnPreDraw { startPostponedEnterTransition() }
    }
    private fun loadPokemonData(view: View, id: String) {
        viewModel.loadPokemonById(id)
        val progressView = view.findViewById<ProgressBar>(R.id.progress)
        val contentView = view.findViewById<View>(R.id.content_group)
        val errorView = view.findViewById<TextView>(R.id.error_message_text)
        viewModel.viewState().observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
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

    private fun goToPokedexEro() {
        adapter = MainAdapter {
            navigation2?.openPokedexEro()
        }
    }

    private fun showDataState(view: View, state: DetailViewState.Data) {
        val head = view.findViewById<ImageView>(R.id.header)
        val nameTextView = view.findViewById<TextView>(R.id.name)
        val imagePreview = view.findViewById<ImageView>(R.id.image)
        val abilitiesTextView = view.findViewById<TextView>(R.id.abilities)
        val height = view.findViewById<TextView>(R.id.height)
        val weight = view.findViewById<TextView>(R.id.weight)
        val hpP = view.findViewById<ProgressView>(R.id.progress_hp)
        val expP = view.findViewById<ProgressView>(R.id.progress_exp)
        val attakP = view.findViewById<ProgressView>(R.id.progress_attak)
        val deffP = view.findViewById<ProgressView>(R.id.progress_defense)
        val speedP = view.findViewById<ProgressView>(R.id.progress_speed)

        fun getTypeColor(types: List<String>): Int {
            return when {
                types.contains("fighting") -> R.color.fighting
                types.contains("flying") -> R.color.flying
                types.contains("poison") -> R.color.poison
                types.contains("ground") -> R.color.ground
                types.contains("rock") -> R.color.rock
                types.contains("bug") -> R.color.bug
                types.contains("ghost") -> R.color.ghost
                types.contains("steel") -> R.color.steel
                types.contains("fire") -> R.color.fire
                types.contains("water") -> R.color.water
                types.contains("grass") -> R.color.grass
                types.contains("electric") -> R.color.electric
                types.contains("psychic") -> R.color.psychic
                types.contains("ice") -> R.color.ice
                types.contains("dragon") -> R.color.dragon
                types.contains("fairy") -> R.color.fairy
                types.contains("dark") -> R.color.dark
                else -> R.color.white
            }
        }
        head.setBackgroundColor(getTypeColor(state.types))
        nameTextView.text = viewModel.lat2cyr(state.name.toUpperCase()) +"\n" +state.types
        height.text = state.height.toString()
        weight.text = state.weight.toString()
        abilitiesTextView.text = state.abilities.toString()
        val hpNew = state.stats["hp"]
        hpP.labelText = "Health : $hpNew/${PokemonEntity.maxHp}"
        hpP.max = PokemonEntity.maxHp.toFloat()
        if (hpNew != null) {
            hpP.progress = hpNew.toFloat()
        }
        val exp: Int = (PokemonEntity.minExp..PokemonEntity.maxExp).random()
        if (exp > 100) {
            view.findViewById<Button>(R.id.floatingActionButton).isVisible = true
            // if (exp in 500..555){ view.findViewById<Button>(R.id.floatingActionButton).isVisible = true
            view.findViewById<TextView>(R.id.surprise).isVisible = true
        }
        fun getExpString(): String = "Experience : $exp/${PokemonEntity.maxExp}"
        expP.labelText = getExpString()
        expP.max = PokemonEntity.maxExp.toFloat()
        expP.progress = exp.toFloat()
        val attak = state.stats["attack"]
        attakP.labelText = "Attack : $attak/${PokemonEntity.maxAttack}"
        attakP.max = PokemonEntity.maxAttack.toFloat()
        if (attak != null) {
            attakP.progress = attak.toFloat()
        }
        val def = state.stats["defense"]
        deffP.labelText = "Defense : $def/${PokemonEntity.maxDefense}"
        deffP.max = PokemonEntity.maxDefense.toFloat()
        if (def != null) {
            deffP.progress = def.toFloat()
        }
        val speed = state.stats["speed"]
        speedP.labelText = "Speed : $speed/${PokemonEntity.maxSpeed}"
        speedP.max = PokemonEntity.maxSpeed.toFloat()
        if (speed != null) {
            speedP.progress = speed.toFloat()
        }
        Picasso.get().load(state.imageUrl).into(imagePreview)
        abilitiesTextView.text = state.abilities.joinToString(separator = ",") { it }
    }
}

