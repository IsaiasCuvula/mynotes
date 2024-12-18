package com.bersyte.mynotes.utils
import android.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

class AppHelper {

    companion object{
        fun generateColor(): Int{
            val red = Random.nextInt(0, 256)
            val green = Random.nextInt(0, 256)
            val blue = Random.nextInt(0, 256)
            return Color.rgb(red, green, blue)
        }

        fun currentDateTime(): LocalDateTime {
            val currentMoment = Clock.System.now()
            return currentMoment.toLocalDateTime(
                TimeZone.currentSystemDefault()
            )
        }
    }

}
