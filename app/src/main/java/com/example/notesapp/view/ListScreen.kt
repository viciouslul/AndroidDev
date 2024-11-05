package com.example.notesapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.controller.Controller
import com.example.notesapp.entity.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController, notesController: Controller) {
    val errorMessage = remember { mutableStateOf("") }
    val notes = notesController.getNotes()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Notes")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("note")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (errorMessage.value.isNotEmpty()) {
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            LazyColumn {
                items(notes) { note ->
                    NoteListItem(
                        note = note,
                        onEditPressed = { note ->
                            navController.navigate("note/${note.id}")
                        },
                        onDeleteButtonPressed = { noteID ->
                            notesController.deleteNote(noteID)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteListItem(
    note: Note,
    onEditPressed: (Note) -> Unit,
    onDeleteButtonPressed: (noteID: String) -> Unit
) {
    val openNote = remember { mutableStateOf(false) }

    Card(
        onClick = { openNote.value = !openNote.value },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = note.text,
                maxLines = if (openNote.value) Int.MAX_VALUE else 2
            )
        }
    }
    if (openNote.value) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onEditPressed(note) }) { Text("Edit") }
            Button(onClick = { onDeleteButtonPressed(note.id) }) { Text("Delete") }
        }
    }
}