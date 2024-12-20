package com.bersyte.mynotes.features.notes.viewmodels
import android.util.Log
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

    private val _noteListState = MutableStateFlow(NoteState<List<Note>>())
    //Ready only - O código fora da ViewModel só pode observar esse estado,
    // mas não pode modificá-lo
    val noteListState = _noteListState.asStateFlow()

    private val _noteDetailState = MutableStateFlow(NoteState<Note>())
    //Ready only
    val noteDetailState = _noteDetailState.asStateFlow()

    init {
        getAllNotes()
    }

     fun getNoteById(noteId: Int) = viewModelScope.launch {
         _noteDetailState.update { it.copy(isLoading = true) }

         try {
             repository.getNoteById(noteId).collect{ note ->
                 _noteDetailState.update {
                     it.copy(isLoading = false, data = note)
                 }
             }
         } catch (e: Exception) {
             Log.d("Fetch note by id", "Error: $e")
             _noteDetailState.update {
                 it.copy(isLoading = false, error = e.message)
             }
         }
     }

    fun updateNote(note: Note) = viewModelScope.launch {
        try {
            repository.updateNote(note)
        }catch (e:Exception){
            Log.d("Update note", "Error: $e")
            _noteListState.update {
                it.copy(isLoading = false, error = e.message)
            }
            return@launch
        }
    }

    fun deleteNote(note: Note)= viewModelScope.launch {
        try {
            repository.deleteNote(note)
        }catch (e:Exception){
            Log.d("Delete note", "Error: $e")
            _noteListState.update {
                it.copy(isLoading = false, error = e.message)
            }
            return@launch
        }
    }

    private fun getAllNotes() = viewModelScope.launch {
        try {
            repository.getAllNotes().collect{ notes ->
                _noteListState.update { nState ->
                    nState.copy(data = notes, isLoading = false)
                }
            }
        }catch (e: Exception){
            Log.d("Get all notes", "Error: $e")
            _noteListState.update {
                it.copy(isLoading = false, error = e.message)
            }
            return@launch
        }
    }

    fun saveNote(title: String, note: String) = viewModelScope.launch {
        val newNote = Note(
            id = 0,
            title = title,
            note = note,
            color = AppHelper.generateColor(),
        )

        _noteListState.update { it.copy(isLoading = true) }
        try {
            repository.insertNote(newNote)
        }catch (e: Exception){
            _noteListState.update {
                it.copy(isLoading = false, error = e.message)
            }
            return@launch
        }
        _noteListState.update { it.copy(isLoading = false) }
    }

}

data class NoteState<T>(
    val data: T? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
