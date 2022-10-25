package com.bokuno.notes

import android.opengl.Visibility
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bokuno.notes.models.Note
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat


class NoteAdapter(private val item: ArrayList<Note>): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.findViewById(R.id.tvNote)
        val titleText: TextView = itemView.findViewById(R.id.tvTitle)
        val createdAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val model=item[position]
        holder.noteText.text=model.text
        holder.titleText.text=model.title
        val formatter = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm")
        val createdAt = formatter.format(model.createdAt)
        holder.createdAt.text=createdAt
        if(model.location == null){
            holder.location.visibility=View.GONE
        }
        holder.location.text=model.location
    }

    override fun getItemCount(): Int {
        return item.size
    }
}