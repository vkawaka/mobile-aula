package br.senai.sp.rickandmorty.model

import androidx.compose.runtime.MutableState

data class Episode (
    val id : Int = 0,
    val name: String = "",
    val air_date: String = "",
    val episode: String = "",
    val characters: List<String> = listOf(),
    val url: String = "",
    val created: String = ""
)
