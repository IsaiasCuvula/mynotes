package com.bersyte.mynotes.features.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bersyte.mynotes.common.components.LoadingIndicator
import com.bersyte.mynotes.common.navigation.Routes
import com.bersyte.mynotes.features.notes.viewmodels.NoteViewModel
import com.bersyte.mynotes.common.components.NotesGridView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    vm: NoteViewModel = hiltViewModel()
) {

    val noteState = vm.noteListState.collectAsState()
    val notesValue = noteState.value

    val  colorScheme = MaterialTheme.colorScheme

    val vArrangement =  if(notesValue.data?.isEmpty() == true){
        Arrangement.Center
    }else{
        Arrangement.Top
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text("My Notes")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.background,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.SearchNote.name)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Search,
                            contentDescription = "Search note icon button",
                            tint = colorScheme.background
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.AddNote.name)
                }

            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Add note button"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = vArrangement
        ) {
            when {
                notesValue.isLoading -> {
                    LoadingIndicator()
                }
                notesValue.error != null -> {
                    Text("Something went wrong \n${noteState.value.error}" )
                }
                notesValue.data != null -> {
                    val notes = notesValue.data
                    NotesGridView(notes, navController)
                }
            }
        }
    }
}
