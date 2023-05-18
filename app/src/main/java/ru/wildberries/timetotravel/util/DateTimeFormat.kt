package ru.wildberries.timetotravel.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun String.dateTimeFormat(): String {
    val dateOnly = this.substringBefore(" ")
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormat = DateTimeFormatter.ofPattern("d MMMM", Locale.getDefault())
    val formatDate = LocalDate.parse(dateOnly, inputFormat)
    return formatDate.format(outputFormat)
}