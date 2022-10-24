package com.bokuno.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bokuno.notes.daos.NoteDao
import com.bokuno.notes.databinding.ActivityCreateNoteBinding

class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener{
            val noteDao=NoteDao()
            val title=binding.etTitle.text.toString().trim()
            val note=binding.etNote.text.toString().trim()
            if(title.isNotEmpty() && note.isNotEmpty()){
                noteDao.addNote(title,note)
                finish()
            }
            else{
                Toast.makeText(this,"Fill the fields",Toast.LENGTH_SHORT).show()
            }
        }
    }
}