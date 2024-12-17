package com.bersyte.mynotes.utils
import android.graphics.Color
import kotlin.random.Random

class AppHelper {

    fun generateColor(): Int{
        val red = Random.nextInt(0, 256)
        val green = Random.nextInt(0, 256)
        val blue = Random.nextInt(0, 256)
        return Color.rgb(red, green, blue)
    }
}
