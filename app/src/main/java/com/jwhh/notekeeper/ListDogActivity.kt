package com.jwhh.notekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.JsonObject
import com.jwhh.notekeeper.adapters.DogListAdapter
import com.jwhh.notekeeper.models.DogModel
import com.jwhh.notekeeper.services.DogApi
import com.jwhh.notekeeper.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_list_dog.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListDogActivity : AppCompatActivity() {
    var dogList: MutableList<DogModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_dog)
        geListBreed()
    }

    fun geListBreed() {
        var dogServicer = ServiceBuilder.buildServiceScalar(DogApi::class.java)
        //authen
        var call = dogServicer.getBreedList()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(403 == response.code())  {
                    //token expired
                    //todo
                }
                var jsonObject = JSONObject(response.body())
                var message = jsonObject.get("message") as JSONObject
                for (nameBreed in message.keys()) {
                    dogList.add(DogModel(nameBreed, "Dog"))
                }
                var adapterDog = DogListAdapter(applicationContext, dogList)
                RecyclerViewDog.layoutManager = LinearLayoutManager(applicationContext)
                RecyclerViewDog.adapter = adapterDog

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed sorry :(", Toast.LENGTH_SHORT).show()
            }

        })
    }
}