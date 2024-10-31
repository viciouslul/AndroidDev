package com.example.notesapp.controller

import com.example.notesapp.entity.Note
import com.example.notesapp.model.Model

class Controller {
    val model: Model = Model()

    fun insertNew(title: String, text: String) {
        val note = Note(title, text)
        model.insertNode(note)
    }
}