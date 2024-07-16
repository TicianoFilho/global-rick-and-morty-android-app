package com.example.globalrickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService: RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        rickAndMortyService.getCharacterById().enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, response: Response<Any>) {
               Log.i("MainActivity", response.toString())
            }

            override fun onFailure(p0: Call<Any>, error: Throwable) {
                Log.i("MainActivity", error.message ?: "Null message")
            }
        })

    }
}