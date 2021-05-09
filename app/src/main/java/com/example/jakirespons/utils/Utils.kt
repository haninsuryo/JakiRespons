package com.example.jakirespons.utils

object Utils {
    fun minToString(runtime: Int): String {
        val durationHours: Int = runtime / 60
        val durationMinutes: Int = runtime % 60

        var str = ""
        if (durationHours > 0) {
            str += "${durationHours}h "
        }
        str += "${durationMinutes}min"

        return str
    }
}