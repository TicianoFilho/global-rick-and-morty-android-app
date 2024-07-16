package com.example.globalrickandmorty

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {
    @GET(value = "character/2 ")
    fun getCharacterById(): Call<CharacterResponse>
}