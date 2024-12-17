package com.bersyte.mynotes.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CommonTextField(
    value: String,
    onValueChange:(String) -> Unit,
    placeholder: @Composable ()-> Unit,
    singleLine: Boolean = false
) {

    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        placeholder = placeholder,
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.onPrimary,
            focusedTextColor = Color.Black,
            unfocusedContainerColor = colorScheme.onPrimary,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = colorScheme.primary,
        )
    )
}
