package com.jwhh.notekeeper

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NoteRecylcerAdapter(private val context: Context, private val notes: List<NoteInfo>) : RecyclerView.Adapter<NoteRecylcerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCourse = itemView.findViewById<TextView>(R.id.textViewTitle)
        val textTitle = itemView.findViewById<TextView>(R.id.textViewText)
        val textDescription = itemView.findViewById<TextView>(R.id.textViewDescription)
        var notePosition = 0

        // click event
        init {
            itemView.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(NOTE_POSITION, notePosition)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textCourse.text = note.course?.title
        holder.textTitle.text = note.title
        holder.textDescription.text = note.text
        holder.notePosition = position
    }

    override fun getItemCount() = notes.size
}