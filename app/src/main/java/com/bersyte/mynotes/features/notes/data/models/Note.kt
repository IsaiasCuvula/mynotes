package com.bersyte.mynotes.features.notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bersyte.mynotes.utils.AppHelper
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var note: String,
    val color: Int,
    val createdAt: LocalDateTime = AppHelper.currentDateTime()
)
