package com.jwhh.notekeeper.presentation.adapters

import android.content.Context
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.data.models.DogModel
import com.jwhh.notekeeper.data.models.ImageDogModel
import com.jwhh.notekeeper.presentation.services.DogApi
import com.jwhh.notekeeper.presentation.services.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.URL

class DogListAdapter(private val context: Context, private val dogs: MutableList<DogModel>) :
        RecyclerView.Adapter<DogListAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBreed = itemView?.findViewById<TextView?>(R.id.textViewBreed)
        val textName = itemView?.findViewById<TextView?>(R.id.textViewNameDog)
        val btnGetImageDog = itemView?.findViewById<Button>(R.id.buttonImageDog)
        val imageViewDog = itemView?.findViewById<ImageView>(R.id.imageViewDog)
        var dogPostition = 0

        init {
            btnGetImageDog?.setOnClickListener {
                val service = ServiceBuilder.buildService(DogApi::class.java)
                var callImage = service.getImageBreed(dogs[dogPostition].nameBreed)
                callImage.enqueue(object : Callback<ImageDogModel> {
                    override fun onResponse(call: Call<ImageDogModel>, response: Response<ImageDogModel>) {
                        if(403 == response.code())  {
                            //token expired
                            //todo
                        }
                        var imageDogModel = response.body()
                        if (imageViewDog != null && imageDogModel != null) {
                            CoroutineScope(IO).launch {
                                SetImageFromURL(imageDogModel.message)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ImageDogModel>, t: Throwable) {
                        Toast.makeText(context, "Error can't get immage", Toast.LENGTH_LONG).show()
                    }
                })

                // other things
            }
        }

        private suspend fun SetImageFromURL(url: String) {
            val strem: InputStream = URL(url).openStream()
            val bmp = BitmapFactory.decodeStream(strem)
            withContext(Main) {
                imageViewDog.setImageBitmap(bmp)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_dog_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogs[position]
        holder.textBreed?.text = dog.nameBreed
        holder.textName?.text = dog.nameDog
        holder.dogPostition = position
        holder.imageViewDog.setImageResource(R.drawable.ic_menu_slideshow)
    }

    override fun getItemCount() = dogs.size
}