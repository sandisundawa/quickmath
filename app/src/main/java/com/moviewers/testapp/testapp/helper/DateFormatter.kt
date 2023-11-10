package com.moviewers.testapp.testapp.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date: String): String {
        val localDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return formatter.format(localDate)
    }
}