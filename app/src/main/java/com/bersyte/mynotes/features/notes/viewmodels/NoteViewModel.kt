package com.bersyte.mynotes.features.notes.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bersyte.mynotes.features.notes.data.models.Note
import com.bersyte.mynotes.features.notes.data.repositories.NoteRepository
import com.bersyte.mynotes.utils.AppHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel  @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _noteState = MutableStateFlow(NoteState())
    val noteState = _noteState.asStateFlow()

    fun saveNote(title: String, note: String) = viewModelScope.launch {
        val newNote = Note(
            id = 0,
            title = title,
            note = note,
            color = AppHelper.generateColor(),
        )

        _noteState.update { it.copy(isLoading = true) }
        try {
            repository.insertNote(newNote)
        }catch (e: Exception){
            _noteState.update { it.copy(isLoading = false) }
            return@launch
        }
        _noteState.update { it.copy(isLoading = false) }
    }

}

data class NoteState(
    val error: String? = null,
    val isLoading: Boolean = false
)
