package com.barkwatch

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ReportManager {

    private var barkCount = 0

    fun barkDetected() {
        barkCount++
    }

    fun generateReport(context: Context) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val reportFile = File(context.getExternalFilesDir(null), "bark_report_$date.txt")
        reportFile.writeText("Total Bark Events: $barkCount")
        barkCount = 0
    }
}