package com.nimdokai.core_util.date

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

interface DateFormatter {
    fun formatOnlyHourIfToday(date: String): String
    fun format(epochTime: Int, outputFormat: String): String
    fun isToday(epochDate: Int): Boolean
}

internal object DateFormatterDefault : DateFormatter {
    @SuppressLint("SimpleDateFormat")
    override fun formatOnlyHourIfToday(date: String): String {
        val parsedDate = try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            format.parse(date)
        } catch (e: ParseException) {
            return date
        }

        val calendar = Calendar.getInstance()
        calendar.time = parsedDate

        val timeFormat = if (isToday(calendar)) {
            SimpleDateFormat("HH:mm")
        } else {
            SimpleDateFormat("dd-MM-yyyy HH:mm")
        }
        return timeFormat.format(parsedDate)
    }

    override fun format(epochTime: Int, outputFormat: String): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = epochTime * 1000L
        val dateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    override fun isToday(epochDate: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = epochDate * 1000L
        return isToday(calendar)
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