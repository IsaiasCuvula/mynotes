package com.bersyte.mynotes.features.notes.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bersyte.mynotes.features.notes.data.models.Note
import com.bersyte.mynotes.utils.Converters


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
