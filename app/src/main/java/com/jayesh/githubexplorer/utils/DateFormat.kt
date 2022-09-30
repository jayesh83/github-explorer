package com.jayesh.githubexplorer.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val readableTimePattern = "h:mm a"
private const val readableDatePattern = "dd/mm/yyyy"
private const val githubDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

/* example github date: 2022-10-30T19:01:12Z is in ISO 8601 format YYYY-MM-DDTHH:MM:SSZ */
fun formatGithubDate(date: String?): String {
    if (date == null) {
        return ""
    }

    val parsedDate: Date? = SimpleDateFormat(githubDatePattern, Locale.getDefault()).parse(date)

    return when {
        parsedDate == null -> extractedGithubDate(date)

        DateUtils.isToday(parsedDate.time) -> SimpleDateFormat(readableTimePattern, Locale.getDefault()).format(parsedDate).lowercase()

        DateUtils.isToday(parsedDate.time + DateUtils.DAY_IN_MILLIS) -> "Yesterday " + SimpleDateFormat(
            readableTimePattern,
            Locale.getDefault()
        ).format(parsedDate).lowercase()

        else -> SimpleDateFormat(readableDatePattern, Locale.getDefault()).format(parsedDate)
    }
}

private fun extractedGithubDate(date: String): String {
    val (year, month, day) = date.substringBefore('T').split('-')
    return "$day/$month/$year"
}
