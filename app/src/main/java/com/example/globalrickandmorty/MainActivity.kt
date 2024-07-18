package com.example.globalrickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the component fom its id which is set in layout
        val nameTextView = findViewById<AppCompatTextView>(R.id.nameTextView)
        val headerImageView = findViewById<AppCompatImageView>(R.id.headerImageView)
        val genderImageView = findViewById<AppCompatImageView>(R.id.genderImageView)
        val aliveTextView = findViewById<AppCompatTextView>(R.id.aliveTextView)
        val originTextView = findViewById<AppCompatTextView>(R.id.originTextView)
        val speciesTextView = findViewById<AppCompatTextView>(R.id.speciesTextView)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService: RickAndMortyService =
            retrofit.create(RickAndMortyService::class.java)
        rickAndMortyService.getCharacterById(54).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                p0: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                Log.i("MainActivity", response.toString())

                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Unsuccessful network call!",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                val body = response.body()!!
                nameTextView.text = body.name
                originTextView.text = body.origin.name
                speciesTextView.text = body.species
                aliveTextView.text = body.status
                Picasso.get().load(body.image).into(headerImageView);
                if (body.gender.equals("male", true)) {
                    genderImageView.setImageResource(R.drawable.ic_male_24)
                } else {
                    genderImageView.setImageResource(R.drawable.ic_female_24)
                }
            }

            override fun onFailure(p0: Call<CharacterResponse>, error: Throwable) {
                Log.i("MainActivity", error.message ?: "Null message")
            }
        })

    }
}