package com.bokuno.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bokuno.notes.daos.NoteDao
import com.bokuno.notes.databinding.ActivityMainBinding
import com.bokuno.notes.models.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject


class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener, SearchView.OnQueryTextListener {

    private lateinit var mAdapter:NoteAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mNoteDao: NoteDao
    private lateinit var noteList: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mNoteDao= NoteDao()
        binding.btnAdd.setOnClickListener {
            val cIntent=Intent(this,CreateNoteActivity::class.java)
            startActivity(cIntent)
        }
        binding.searchBar.setOnQueryTextListener(this)
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        noteList=ArrayList<Note>()
        mNoteDao.noteCollection
            .whereEqualTo("userId",mNoteDao.mAuth.currentUser?.uid)
            .orderBy("createdAt",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    val note=dc.document.toObject<Note>()
                    if (dc.type == DocumentChange.Type.ADDED) {
                        noteList.add(note)
                        Log.d(TAG, "Note added: ${note.title}")
                    }
                    if(dc.type == DocumentChange.Type.REMOVED) {
                        noteList.remove(note)
                        Log.d(TAG, "Note removed: ${note.title}")
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        mAdapter= NoteAdapter(noteList)
        binding.recyclerView.adapter=mAdapter
        binding.recyclerView.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
    }

    override fun onAuthStateChanged(fAuth: FirebaseAuth) {
        if(fAuth.currentUser==null){
            val lIntent=Intent(this,LoginActivity::class.java)
            startActivity(lIntent)
            return
        }
        setUpRecyclerView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(search: String?): Boolean {
        noteList.clear()
        mNoteDao.noteCollection
            .whereEqualTo("userId",mNoteDao.mAuth.currentUser?.uid)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    val note=dc.document.toObject<Note>()
                    if(note.title!!.contains(search!!))
                        noteList.add(note)
                }
                mAdapter.notifyDataSetChanged()
            }
        return true
    }

    private companion object{
        private const val TAG="Mainxy"
    }
}

