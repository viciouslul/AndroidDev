package com.example.notesapp.controller

import com.example.notesapp.entity.Note
import com.example.notesapp.model.Model
import java.util.UUID

class Controller() {
    val model: Model = Model()

    fun getNotes() = model.notes

    fun getNoteByID(noteID: String): Note? {
        val note = this.model.findNoteByID(noteID);
        return note;
    }

    fun insertNew(title: String, text: String): String? {
        if (title.isBlank() || text.isBlank()) {
            return "Cannot be empty!"
        }
        if (text.length > 120) {
            return "Text cannot be more than 120 characters!"
        }
        if (title.length > 50) {
            return "Title cannot be more than 50 characters!"
        }
        
        val note = Note(UUID.randomUUID().toString(), title, text)

        model.insertNode(note)
        return null
    }

    fun editNote(title: String, text: String, note: Note): String? {
        if (title.isBlank() || text.isBlank()) {
            return "Cannot be empty!"
        }
        if (text.length > 120) {
            return "Text cannot be more than 120 characters!"
        }
        if (title.length > 50) {
            return "Title cannot be more than 50 characters!"
        }
        model.editNode(Note(note.id, title, text))
        return null
    }

    fun deleteNote(noteID: String) {
        model.deleteNoteByID(noteID)
    }

}