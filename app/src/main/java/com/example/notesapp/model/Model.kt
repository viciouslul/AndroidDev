package com.example.notesapp.model

import com.example.notesapp.entity.Note

class Model {

    val notes: MutableList<Note> = mutableListOf()

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