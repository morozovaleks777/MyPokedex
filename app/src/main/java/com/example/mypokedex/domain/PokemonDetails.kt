package com.example.mypokedex.domain

data class PokemonDetails(
    val id: String,
//    val name: String,
//    val imageUrl: String,

    val hp: Int = (minHp..maxHp).random(),
    val attack: Int = (minAttack..maxAttack).random(),
    val defense: Int = (minDefense..maxDefense).random(),
    val speed: Int = (minSpeed..maxSpeed).random(),
    val exp: Int = (minExp..maxExp).random()
) {

    fun getHpString(): String = "Health : $hp/$maxHp"
    fun getAttackString(): String = "Attack : $attack/$maxAttack"
    fun getDefenseString(): String = "Defence : $defense/$maxDefense"
    fun getSpeedString(): String = "Speed : $speed/$maxSpeed"
    fun getExpString(): String = "Experience : $exp/$maxExp"

    companion object {
        const val minHp = 100
        const val maxHp = 300
        const val minAttack = 30
        const val maxAttack = 300
        const val minDefense = 50
        const val maxDefense = 300
        const val minSpeed = 40
        const val maxSpeed = 300
        const val minExp = 10
        const val maxExp = 1000

    }
}
