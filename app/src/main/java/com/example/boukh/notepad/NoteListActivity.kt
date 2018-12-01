package com.example.boukh.notepad

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.boukh.notepad.utils.deleteNote
import com.example.boukh.notepad.utils.loadNotes
import com.example.boukh.notepad.utils.persistNote

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        notes = loadNotes(this)


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        findViewById<FloatingActionButton>(R.id.creat_note_fab).setOnClickListener(this)

        adapter = NoteAdapter(notes,this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode){
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processeEditNoteResult(data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun processeEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        when (data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }


    }

    override fun onClick(view: View) {
        if (view.tag != null){
            showNoteDetail(view.tag as Int)
        } else {
            when (view.id) {
                R.id.creat_note_fab -> creatNewNote()
            }
        }
    }

    fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this, note)
        if (noteIndex < 0){
            notes.add(0, note)
        }else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun deleteNote(noteIndex: Int) {
        if (noteIndex < 0){
            return
        } else {
            val note = notes.removeAt(noteIndex)
            deleteNote(this, note)
            adapter.notifyDataSetChanged()

            Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun creatNewNote() {
        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex: Int){
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }
}