package com.barkwatch

import android.content.Context
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import org.tensorflow.lite.task.audio.TensorAudio

class BarkDetector(private val context: Context) {

    private val classifier = AudioClassifier.createFromFile(context, "yamnet.tflite")

    fun detect(audio: TensorAudio): Boolean {
        val results = classifier.classify(audio)
        for (category in results[0].categories) {
            if (category.label == "Dog bark" && category.score > 0.8) {
                return true
            }
        }
        return false
    }
}