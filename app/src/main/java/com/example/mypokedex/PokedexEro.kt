package com.example.mypokedex


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

const val ARG_OBJECT = "object"

class PokedexEro : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokedex_ero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listOfPokemons = arrayListOf(
            R.drawable.lugia_pokedex_1,
            R.drawable.ero_pokemon_2,
            R.drawable.persian_3,
            R.drawable.lopunn_4,
            R.drawable.gardevoir_5,
            R.drawable.delphox_6,
            R.drawable.gardevoir_7,
            R.drawable.mightyena_8,
            R.drawable.pikachu_9,
            R.drawable.taranimart_blaziken_10,
            R.drawable.vader_san_furry_11,
            R.drawable.wyntersun_12,
            R.drawable.pinktaco_13,
            R.drawable.yuuyatails_lugia_13
        )
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text)
            textView.text = getInt(ARG_OBJECT).toString()
            val imageView: ImageView = view.findViewById(R.id.eroImage)
            val i = getInt(ARG_OBJECT)
            imageView.setImageResource(listOfPokemons[i])
            val cardview = view.findViewById<CardView>(R.id.cardView2)
            animateColorValue(cardview)
        }
    }

    private fun animateColorValue(view: View) {
        val colorAnimation =
            ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, Color.RED)
        colorAnimation.duration = 7000L
        colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.start()
    }
}
