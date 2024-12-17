package com.bersyte.mynotes.features.notes.data.repositories

import com.bersyte.mynotes.features.notes.data.models.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    suspend fun getNoteById(id: Int): Flow<Note?>

    suspend fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}
