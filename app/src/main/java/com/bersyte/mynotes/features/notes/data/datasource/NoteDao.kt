package com.bersyte.mynotes.features.notes.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bersyte.mynotes.features.notes.data.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * from notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Flow<Note>

    @Query("SELECT * from notes ORDER BY createdAt ASC")
    suspend fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
