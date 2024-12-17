package com.bersyte.mynotes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bersyte.mynotes.common.navigation.AppNavHost

@Composable
fun MyNotesApp() {

    val navController = rememberNavController()

    AppNavHost(
            navController,
            modifier = Modifier
                .fillMaxSize()
    )
}
