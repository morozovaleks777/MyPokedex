package com.example.mypokedex.presentation.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.domain.PokemonRepository
import com.example.mypokedex.domain.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<DetailViewState>()

    fun viewState(): LiveData<DetailViewState> = viewStateLiveData

    fun loadPokemonById(id: String) {
        viewStateLiveData.value = DetailViewState.Loading

        fun PokemonEntity.toDataViewState() = DetailViewState.Data(
            name = name,
            // name = transliterate(name),
            imageUrl = previewUrl,
            abilities = abilities,
            height = height,
            weight = weight,
            stats = stats,
            types = types,
        )
        viewModelScope.launch {
            delay(2000)
            viewStateLiveData.value = when (val result = repository.getPokemonById(id)) {
                is Result.Success -> {
                    val responseData = result.data
                    responseData.toDataViewState()
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    createErrorViewState("Failed to load pokemon with id=$id")
                }
            }
        }
    }

    private fun createErrorViewState(message: String) = DetailViewState.Error(message)

    fun lat2cyr(s: String): String {
        val sb = StringBuilder(s.length)
        var i = 0
        while (i < s.length) {
            var ch = s[i]
            if (ch == 'J') {
                i++
                ch = s[i]
                when (ch) {
                    'E' -> sb.append('Ё')
                    'S' -> {
                        sb.append('Щ')
                        i++
                        require(s[i] == 'H') { "Illegal transliterated symbol at position $i" } // вариант третьего символа только один
                    }
                    'H' -> sb.append('Ь')
                    'U' -> sb.append('Ю')
                    'A' -> sb.append('Я')
                    else -> throw IllegalArgumentException("Illegal transliterated symbol at position $i")
                }
            } else if (i + 1 < s.length && s[i + 1] == 'H' && !(i + 2 < s.length && s[i + 2] == 'H')) { // Постфиксная нотация, требует информации о двух следующих символах. Для потока придется сделать обертку с очередью из трех символов.
                when (ch) {
                    'Z' -> sb.append('Ж')
                    'K' -> sb.append('Х')
                    'C' -> sb.append('Ч')
                    'S' -> sb.append('Ш')
                    'E' -> sb.append('Э')
                    'H' -> sb.append('Ъ')
                    'I' -> sb.append('Ы')
                    else -> throw IllegalArgumentException("Illegal transliterated symbol at position $i")
                }
                i++
            } else {
                when (ch) {
                    'A' -> sb.append('А')
                    'B' -> sb.append('Б')
                    'V' -> sb.append('В')
                    'G' -> sb.append('Г')
                    'D' -> sb.append('Д')
                    'E' -> sb.append('Е')
                    'Z' -> sb.append('З')
                    'I' -> sb.append('И')
                    'Y' -> sb.append('Й')
                    'K' -> sb.append('К')
                    'L' -> sb.append('Л')
                    'M' -> sb.append('М')
                    'N' -> sb.append('Н')
                    'O' -> sb.append('О')
                    'P' -> sb.append('П')
                    'R' -> sb.append('Р')
                    'S' -> sb.append('С')
                    'T' -> sb.append('Т')
                    'U' -> sb.append('У')
                    'F' -> sb.append('Ф')
                    'C' -> sb.append('Ц')
                    else -> sb.append(ch)
                }
            }
            i++
        }
        return sb.toString()
    }
}