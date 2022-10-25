package com.bokuno.notes.daos

import com.bokuno.notes.models.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteDao {
     val mDB=FirebaseFirestore.getInstance()
     val noteCollection=mDB.collection("notes")
     val mAuth=FirebaseAuth.getInstance()
    fun addNote(title : String, note : String, location : String?) {
        GlobalScope.launch {
            val createdAt=System.currentTimeMillis()
            val note= Note(title,note,createdAt, mAuth.currentUser?.uid.toString(),location)
            noteCollection.document().set(note)
        }
    }
}

