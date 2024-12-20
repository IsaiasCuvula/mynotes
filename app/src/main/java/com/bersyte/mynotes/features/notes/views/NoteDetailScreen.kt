package com.bersyte.mynotes.features.notes.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bersyte.mynotes.common.components.ShowAlertDialog
import com.bersyte.mynotes.common.components.CommonTextField
import com.bersyte.mynotes.common.navigation.Routes
import com.bersyte.mynotes.features.notes.viewmodels.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,
    navController: NavController,
    vm: NoteViewModel = hiltViewModel()
){
    val noteState = vm.noteDetailState.collectAsState()
    var title by remember { mutableStateOf( "") }
    var description by remember { mutableStateOf("") }

    // Calling the method as soon the
    // the user opens the screen
    LaunchedEffect(Unit) {
        vm.getNoteById(noteId)
    }

    val noteValue = noteState.value

    LaunchedEffect(noteValue) {
        //assign initial value
        title = noteValue.data?.title ?: ""
        description = noteValue.data?.note ?:""
    }

    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    val openAlertDialog = remember {  mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit note") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back button",
                            tint = colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.background,
                ),
                actions = {
                    IconButton(
                        onClick = {openAlertDialog.value = true}
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "",
                            tint = colorScheme.background
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val oldNote = noteState.value.data
                    if(oldNote != null){
                        oldNote.title = title
                        oldNote.note = description

                        vm.updateNote(oldNote)

                        Toast.makeText(
                            context,
                            "Note update successfully ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(
                    Icons.Rounded.Save,
                    contentDescription = "Update note button"
                )
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(
                    color = colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            when {
                noteValue.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                noteValue.error != null -> {
                    Text("Something went wrong \n${noteState.value.error}" )
                }
                noteValue.data != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CommonTextField(
                            value = title,
                            textStyle = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            onValueChange = { value ->
                                title = value
                            },
                            placeholder = {
                                Text(
                                    title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            singleLine = true
                        )
                        CommonTextField(
                            value = description,
                            onValueChange = { value ->
                                description = value
                            },
                            placeholder = {
                                Text(
                                    description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = colorScheme.tertiary
                                )
                            },
                        )
                    }

                }
            }
        }

        if(openAlertDialog.value){
            val oldNote = noteState.value.data
            if(oldNote != null){
                ShowAlertDialog(
                    onDismissRequest = {openAlertDialog.value = false},
                    onConfirmation = {
                        vm.deleteNote(oldNote)

                        Toast.makeText(
                            context,
                            "Note deleted successfully ",
                            Toast.LENGTH_SHORT
                        ).show()

                        //navigate to home page
                        navController.navigate(Routes.Home.name){
                            popUpTo(Routes.Home.name) {
                                inclusive = true
                            }
                        }
                    },
                    title = "Delete Note",
                    body = "Are you sure you want to delete this note?",
                    icon = Icons.Rounded.Info,
                )
            }
        }
    }
}
