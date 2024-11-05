package com.example.notesapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.controller.Controller

@Composable
fun NoteDetailsScreen(navController: NavController, notesController: Controller, noteID: String?) {
    var errorMessage by remember { mutableStateOf<String?>(null) }

    if (noteID == null) {
        NoteForm(
            onSave = { title, text ->
                val error = notesController.insertNew(title, text)
                if (error == null) {
                    navController.navigateUp()
                } else {
                    errorMessage = error
                }
            },
            onCancel = { navController.navigateUp() },
            errorMessage = errorMessage
        )
        return
    }

    val note = notesController.getNoteByID(noteID)
    if (note === null) {
        Text("Invalid ID provided")
        return;
    }

    NoteForm(
        defaultTitle = note.title,
        defaultText = note.text,
        onSave = { updatedTitle, updatedText ->
            val error = notesController.editNote(title = updatedTitle, text = updatedText, note)
            if (error == null) {
                navController.navigateUp()
            } else {
                errorMessage = error
            }
        },
        onCancel = { navController.navigateUp() },
        errorMessage = errorMessage
    )
}

@Composable
fun NoteForm(
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit,
    defaultTitle: String = "",
    defaultText: String = "",
    errorMessage: String? = null
) {
    var title by remember { mutableStateOf(defaultTitle) }
    var text by remember { mutableStateOf(defaultText) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = if (defaultTitle.isEmpty()) "New Note" else "Edit Note",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onSave(title, text) }) {
                Text("Save")
            }
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancel")
            }
        }
    }
}
