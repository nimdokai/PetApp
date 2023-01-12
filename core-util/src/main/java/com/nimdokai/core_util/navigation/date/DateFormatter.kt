package com.nimdokai.core_util.navigation.date

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

interface DateFormatter {
    fun formatOnlyHourIfToday(date: String): String
}

internal object DefaultDateFormatter : DateFormatter {

    @SuppressLint("SimpleDateFormat")
    override fun formatOnlyHourIfToday(date: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        @Suppress("NAME_SHADOWING")
        val date: Date = format.parse(date) ?: return date

        val calendar = Calendar.getInstance()
        calendar.time = date

        return if (isToday(calendar)) {
            val timeFormat = SimpleDateFormat("HH:mm")
            return timeFormat.format(date)
        } else {
            val timeFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
            return timeFormat.format(date)
        }
    }

    private fun isToday(
        calendar: Calendar,
    ): Boolean {
        val todayCalendar = Calendar.getInstance()
        todayCalendar.time = Date()
        return (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == todayCalendar.get(Calendar.DATE))
    }
}
