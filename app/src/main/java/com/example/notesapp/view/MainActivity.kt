package com.example.notesapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.notesapp.controller.Controller
import com.example.notesapp.entity.Note
import com.example.notesapp.ui.theme.NotesAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val controller = Controller()
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                Scaffold(
                    notes = controller.model.notes,
                    onConfirm = { title: String, text: String ->
                        controller.insertNew(title, text)
                    })
            }
        }
    }
}

@Composable
fun NotesList(notes: MutableList<Note>) {
    LazyColumn {
        items(notes) { note ->
            println("Displaying note: ${note.title}")
            DisplayNotes(note)
        }
    }
}

@Composable
fun DisplayNotes(note: Note) {
    var openNote by remember { mutableStateOf(false) }

    Card(
        onClick = { openNote = !openNote }, // Toggle expansion
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .animateContentSize() // Smooth transition
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Title text, always visible
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            // Conditionally expanded text content
            Text(
                text = note.text,
                maxLines = if (openNote) Int.MAX_VALUE else 2, // Expand/collapse based on state

            )
        }
    }
    if (openNote) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {}) { Text("Edit") }
            Button(onClick = {}) { Text("Delete") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(notes: MutableList<Note>, onConfirm: (String, String) -> Unit) {
    var openDialog = remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

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
            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            NotesList(notes)
        }
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text("Title:") },
            text = {
                Column {
                    TextField(
                        value = title,
                        onValueChange = { title = it }, //it == user input
                        label = { Text("Enter Title:") }
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Enter Text:") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(title, text)
                    openDialog.value = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { openDialog.value = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}



