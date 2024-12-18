package com.bersyte.mynotes.features.notes.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bersyte.mynotes.features.notes.data.models.Note
import com.bersyte.mynotes.utils.Converters


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private  var db_instance: NoteDatabase? = null

        fun  getDatabase(context: Context): NoteDatabase {
            // if the db_instance is not null, return it,
            // otherwise create a new database instance.
            return db_instance ?: synchronized(this){
                Room.databaseBuilder(
                    context, NoteDatabase::class.java,
                    "note_database"
                ).build().also { db_instance = it }
            }
        }
    }
}
