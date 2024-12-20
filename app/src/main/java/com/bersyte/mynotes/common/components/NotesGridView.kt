package com.bersyte.mynotes.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bersyte.mynotes.common.navigation.Routes
import com.bersyte.mynotes.features.notes.data.models.Note

@Composable
fun NotesGridView(notes: List<Note>, navController: NavController) {

    if(notes.isEmpty()){
       Column(
           modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           NoteEmptyCard()
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
