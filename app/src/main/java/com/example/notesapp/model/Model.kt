package com.example.notesapp.model

import androidx.compose.runtime.mutableStateListOf
import com.example.notesapp.entity.Note

class Model {

    val notes = mutableStateListOf<Note>()

    fun insertNode(note: Note) {
        notes.add(note)
    }

    fun editNode(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
        }
    }
}