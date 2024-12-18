package com.bersyte.mynotes.di

import com.bersyte.mynotes.features.notes.data.datasource.NoteDatabase
import com.bersyte.mynotes.features.notes.data.repositories.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository = NoteRepository(db)
}
