package com.example.globalrickandmorty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {
    @GET(value = "character/{characterId}")
    fun getCharacterById(
        @Path(value = "characterId") characterId: Int
    ): Call<CharacterResponse>
}