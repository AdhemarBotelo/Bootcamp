package com.jwhh.notekeeper.models

import android.net.Uri

class DogModel(val nameBreed: String = "None", val nameDog: String = "DOG", val urlPicture: String = "") {

    fun getImageDog(): Uri {
        var urlImage: Uri = Uri.EMPTY;
        return urlImage;
    }
}

data class ImageDogModel(
        val message: String,
        val status: String
) {}