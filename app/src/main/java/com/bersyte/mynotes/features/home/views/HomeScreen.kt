package com.bersyte.mynotes.features.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bersyte.mynotes.common.components.LoadingIndicator
import com.bersyte.mynotes.common.navigation.Routes
import com.bersyte.mynotes.features.notes.viewmodels.NoteViewModel
import com.bersyte.mynotes.features.notes.views.components.NoteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    vm: NoteViewModel = hiltViewModel()
) {

    val noteState = vm.noteListState.collectAsState()
    val notesValue = noteState.value

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    val screenWidth = config.screenWidthDp
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
                notesValue.data !=null -> {
                    val notes = notesValue.data

                    if(notes.isEmpty()){
                        Box(
                            modifier = Modifier
                                .size(
                                    height = (screenHeight * 0.2).dp,
                                    width = (screenWidth * 0.7).dp
                                )
                                .padding(16.dp)
                                .background(
                                    color = colorScheme.onPrimary,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "There is no note available!!!",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }else {
                        LazyVerticalStaggeredGrid(
                            verticalItemSpacing = 4.dp,
                            columns = StaggeredGridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxSize(),
                            content = {
                                items(notes){ note ->
                                    NoteCard(note, onClick = {
                                        navController.navigate(
                                            "${Routes.NoteDetails.name}/${note.id}"
                                        )
                                    })
                                }
                            }
                        )
                    }
                }
            }

        }
    }
}
