package com.barkwatch

import android.content.Context
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Recorder {

    fun record(context: Context) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val file = File(context.getExternalFilesDir(null), "bark_$timestamp.wav")

        val recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setOutputFile(file.absolutePath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

        recorder.prepare()
        recorder.start()

        Handler(Looper.getMainLooper()).postDelayed({
            recorder.stop()
            recorder.release()
        }, 30000) // 30-second recording
    }
}