package br.com.example.domain.entity

import java.io.Serializable

data class People (
    val id: Int,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: Int,
    val species: Array<Int>,
    val starships: Array<Int>,
    val vehicles: Array<Int>
): Serializable