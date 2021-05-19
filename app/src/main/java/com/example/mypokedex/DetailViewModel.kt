package com.example.mypokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.domain.PokemonDetails

import com.example.mypokedex.domain.PokemonEntity
import com.example.mypokedex.domain.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.mypokedex.domain.Result
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

class DetailViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<DetailViewState>()
    private val _pokemonDetailsLiveData = MutableLiveData<PokemonDetails>()
    val pokemonDetailsLiveData: LiveData<PokemonDetails> = _pokemonDetailsLiveData

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

    fun transliterate(message: String): String? {
        val abcCyr = arrayOf(
            "",
            "а",
            "б",
            "в",
            "г",
            "д",
            "е",
            "ё",
            "ж",
            "з",
            "и",
            "й",
            "к",
            "л",
            "м",
            "н",
            "о",
            "п",
            "р",
            "с",
            "т",
            "у",
            "ф",
            "х",
            "ц",
            "ч",
            "ш",
            "щ",
            "ъ",
            "ы",
            "ь",
            "э",
            "ю",
            "я",
            "А",
            "Б",
            "В",
            "Г",
            "Д",
            "Е",
            "Ё",
            "Ж",
            "З",
            "И",
            "Й",
            "К",
            "Л",
            "М",
            "Н",
            "О",
            "П",
            "Р",
            "С",
            "Т",
            "У",
            "Ф",
            "Х",
            "Ц",
            "Ч",
            "Ш",
            "Щ",
            "Ъ",
            "Ы",
            "Ь",
            "Э",
            "Ю",
            "Я",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z "
        )

        val abcLat = arrayOf(
            "",
            "a",
            "b",
            "v",
            "g",
            "d",
            "e",
            "e",
            "zh",
            "z",
            "i",
            "y",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "r",
            "s",
            "t",
            "u",
            "f",
            "h",
            "ts",
            "ch",
            "sh",
            "sch",
            "",
            "i",
            "",
            "e",
            "ju",
            "ja",
            "A",
            "B",
            "V",
            "G",
            "D",
            "E",
            "E",
            "Zh",
            "Z",
            "I",
            "Y",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "R",
            "S",
            "T",
            "U",
            "F",
            "H",
            "Ts",
            "Ch",
            "Sh",
            "Sch",
            "",
            "I",
            "",
            "E",
            "Ju",
            "Ja",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
        val builder = StringBuilder()
        for (element in message) {
            for (x in abcLat.indices) {
                if (element.toString() == abcLat[x]) {
                    builder.append(abcCyr[x])
                }

            }
        }
        return builder.toString()
    }


    private fun createErrorViewState(message: String) = DetailViewState.Error(message)


    fun lat2cyr(s: String): String? {
        val sb = StringBuilder(s.length)
        var i = 0
        while (i < s.length) { // Идем по строке слева направо. В принципе, подходит для обработки потока
            var ch = s[i]
            if (ch == 'J') { // Префиксная нотация вначале
                i++ // преходим ко второму символу сочетания
                ch = s[i]
                when (ch) {
                    'E' -> sb.append('Ё')
                    'S' -> {
                        sb.append('Щ')
                        i++ // преходим к третьему символу сочетания
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
                i++ // пропускаем постфикс
            } else { // одиночные символы
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
            i++ // переходим к следующему символу
        }
        return sb.toString()
    }
}