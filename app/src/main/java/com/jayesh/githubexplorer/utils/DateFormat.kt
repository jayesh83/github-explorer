package com.jayesh.githubexplorer.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val readableTimePattern = "h:mm a"
private const val readableDatePattern = "dd/MM/yyyy"

/* example github date: 2022-10-30T19:01:12Z is in ISO 8601 format YYYY-MM-DDTHH:MM:SSZ */
fun formatGithubDate(date: Date?): String {
    if (date == null) {
        return ""
    }

    return when {
        DateUtils.isToday(date.time) -> SimpleDateFormat(readableTimePattern, Locale.getDefault()).format(date).lowercase()

        DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS) -> "Yesterday " + SimpleDateFormat(
            readableTimePattern,
            Locale.getDefault()
        ).format(date).lowercase()

        else -> SimpleDateFormat(readableDatePattern, Locale.getDefault()).format(date)
    }
}