package com.barkwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.tensorflow.lite.task.audio.TensorAudio

class MainActivity : AppCompatActivity() {

    private lateinit var detector: BarkDetector
    private lateinit var recorder: Recorder
    private lateinit var reporter: ReportManager

    private lateinit var statusText: TextView
    private lateinit var startButton: Button

    private var monitoringJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        detector = BarkDetector(this)
        recorder = Recorder()
        reporter = ReportManager()

        statusText = findViewById(R.id.statusText)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startMonitoring()
        }
    }

    private fun startMonitoring() {
        monitoringJob = CoroutineScope(Dispatchers.Default).launch {
            val tensorAudio = TensorAudio.createFromMicrophone(this@MainActivity)

            while (isActive) {
                tensorAudio.load()
                if (detector.detect(tensorAudio)) {
                    reporter.barkDetected()
                    recorder.record(this@MainActivity)
                    statusText.post { statusText.text = "Bark detected! Recording..." }
                    delay(30000) // Wait for recording to finish
                }
                delay(500) // Check twice per second
            }
        }
        startButton.isEnabled = false
        statusText.text = "Monitoring started..."
    }

    override fun onDestroy() {
        monitoringJob?.cancel()
        reporter.generateReport(this)
        super.onDestroy()
    }
}