package com.example.boukh.notepad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

class NoteListActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        if (view != null) {
            if (view.tag != null){
                Log.i("NoteListActivity", "Click")
            }
        }
    }

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        notes = mutableListOf<Note>()
        notes.add(Note("Note1", "Bla1"))
        notes.add(Note("Note2", "Bla2"))
        notes.add(Note("Note3", "Bla3"))
        notes.add(Note("Note4", "Bla4"))

        adapter = NoteAdapter(notes,this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
