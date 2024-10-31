package com.example.notesapp.controller

import com.example.notesapp.entity.Note
import com.example.notesapp.model.Model
import java.util.UUID

class Controller {
    val model: Model = Model()

    fun insertNew(title: String, text: String) {
        val note = Note(UUID.randomUUID().toString(), title, text)
        model.insertNode(note)
    }

    fun editNote(title: String, text: String, note: Note) {
        model.editNode(Note(note.id, title, text))
    }
}