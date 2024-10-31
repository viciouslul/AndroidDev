package com.example.notesapp.model

import com.example.notesapp.entity.Note

class Model {

    val notes: MutableList<Note> = mutableListOf(

    )

    fun insertNode(note: Note) {
        notes.add(note)
    }
}