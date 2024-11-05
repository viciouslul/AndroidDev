package com.example.notesapp.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.controller.Controller


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val notesController = Controller()

    NavHost(navController = navController, startDestination = "ListScreen") {
        composable("ListScreen") { ListScreen(navController, notesController) }
        composable("note/{noteID}") { backStackEntry ->
            val noteID = backStackEntry.arguments?.getString("noteID")
            NoteDetailsScreen(navController, notesController, noteID)
        }
        composable("note") { NoteDetailsScreen(navController, notesController, null) }
    }
}