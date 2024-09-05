package br.senai.sp.rickandmorty.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    //url
    private val BASE_URL = "https://rickandmortyapi.com/api/"
    //client
    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCharacterService(): CharacterService{
        return retrofitFactory.create(CharacterService::class.java)
    }
}