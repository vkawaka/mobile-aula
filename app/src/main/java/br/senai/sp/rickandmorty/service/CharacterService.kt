package br.senai.sp.rickandmorty.service

import br.senai.sp.rickandmorty.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>

    @GET("character")
    fun getAllCharacters(): Call<List<Character>>
}