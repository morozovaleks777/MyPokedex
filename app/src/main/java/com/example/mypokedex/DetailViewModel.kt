package com.example.mypokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedex.di.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DetailViewModel: ViewModel() {
    private val repository = Injector.providePokemonRepository()
    private var disposable: Disposable? = null
    private val viewStateLiveData = MutableLiveData<DetailViewState>()

    fun viewState(): LiveData<DetailViewState> = viewStateLiveData

    fun loadPokemonById(id: String) {
        viewStateLiveData.value = DetailViewState.Loading

        disposable = repository.getPokemonById(id)
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pokemonEntity ->
                viewStateLiveData.value = DetailViewState.Data(
                    name = pokemonEntity.name,
                    imageUrl = pokemonEntity.previewUrl,
                    abilities = pokemonEntity.abilities
                )
            }, {
                viewStateLiveData.value = DetailViewState.Error("Failed to load pokemon with id=$id")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}