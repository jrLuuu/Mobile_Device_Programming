package com.example.roomexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class NotesActivity : AppCompatActivity() {

    private lateinit var adapter: NotesRVAdapter

    private val noteDatabase by lazy { NoteDatabase.getDatabase(this).noteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        setRecyclerView()

        observeNotes()
    }

    private fun setRecyclerView() {
        val notesRecyclerview = findViewById<RecyclerView>(R.id.notes_recyclerview)
        notesRecyclerview.layoutManager = LinearLayoutManager(this)
        notesRecyclerview.setHasFixedSize(true)
        adapter = NotesRVAdapter()
        adapter.setItemListener(object : RecyclerClickListener {

            // Tap the 'X' to delete the note.
            override fun onItemRemoveClick(position: Int) {
                val notesList = adapter.currentList.toMutableList()
                val noteText = notesList[position].noteText
                val noteDateAdded = notesList[position].dateAdded
                val removeNote = Note(noteDateAdded, noteText)
                notesList.removeAt(position)
                adapter.submitList(notesList)
                lifecycleScope.launch {
                    noteDatabase.deleteNote(removeNote)
                }
            }

            // Tap the note to edit.
            override fun onItemClick(position: Int) {
                // Get the list of notes
                val notes = adapter.currentList.toMutableList()
                val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
                intent.putExtra("note_date_added", notes[position].dateAdded)
                intent.putExtra("note_text", notes[position].noteText)
                editNoteResultLauncher.launch(intent)
            }
        })
        notesRecyclerview.adapter = adapter
    }

    private val newNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the new note from the AddNoteActivity
                val noteDateAdded = Date()
                val noteText = result.data?.getStringExtra("note_text")
                // Add the new note at the top of the list
                val newNote = Note(noteDateAdded, noteText ?: "")
                lifecycleScope.launch {
                    noteDatabase.addNote(newNote)
                }
            }
        }

    private fun observeNotes() {
        lifecycleScope.launch {
            noteDatabase.getNotes().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    adapter.submitList(notesList)
                }
            }
        }
    }

    val editNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the edited note from the AddNoteActivity
                val noteDateAdded = result.data?.getSerializableExtra("note_date_added") as Date
                val noteText = result.data?.getStringExtra("note_text")
                // Update the note in the list
                val editedNote = Note(noteDateAdded, noteText ?: "")
                lifecycleScope.launch {
                    noteDatabase.updateNote(editedNote)
                }
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The '+' menu button
        if (item.itemId == R.id.add_note_menu_item) {
            // Open AddNoteActivity
            val intent = Intent(this, AddNoteActivity::class.java)
            newNoteResultLauncher.launch(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }
}
