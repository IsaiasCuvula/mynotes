package com.bersyte.mynotes.features.notes.data.repositories

import com.bersyte.mynotes.features.notes.data.datasource.NoteDao
import com.bersyte.mynotes.features.notes.data.datasource.NoteDatabase
import com.bersyte.mynotes.features.notes.data.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(db: NoteDatabase): INoteRepository {

    private val dao: NoteDao = db.noteDao()

    override suspend fun getNoteById(id: Int): Flow<Note?> {
        return dao.getNoteById(id)
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
       return dao.insert(note)
    }

    override suspend fun updateNote(note: Note) {
        return dao.update(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.delete(note)
    }
}
