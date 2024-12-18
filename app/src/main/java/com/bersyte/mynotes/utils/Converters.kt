package com.bersyte.mynotes.utils

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime



class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime {
        return value?.let { LocalDateTime.parse(it) } ?: AppHelper.currentDateTime()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String {
        return date?.toString() ?: AppHelper.currentDateTime().toString()
    }
}
