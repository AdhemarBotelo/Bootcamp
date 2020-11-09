package com.jwhh.notekeeper.services

import com.jwhh.notekeeper.models.ImageDogModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface DogApi {

    @GET("breeds/list/all")
    fun getBreedList(): Call<String>

    @GET("breed/{breed}/images/random")
    fun getImageBreed(@Path("breed") breed: String): Call<ImageDogModel>

}