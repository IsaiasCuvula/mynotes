package com.bersyte.mynotes.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bersyte.mynotes.features.home.views.HomeScreen
import com.bersyte.mynotes.features.home.views.SearchNoteScreen
import com.bersyte.mynotes.features.notes.views.AddNote
import com.bersyte.mynotes.features.notes.views.NoteDetailScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Home.name,
        modifier = modifier
    ){
        composable(Routes.Home.name) { HomeScreen(navController)}
        composable(Routes.AddNote.name) { AddNote() }
        composable(Routes.NoteDetails.name) { NoteDetailScreen() }
        composable(Routes.SearchNote.name) { SearchNoteScreen() }
    }

}
