package com.cappulcu.assistant

import android.util.Log
import java.io.File

class PiperTTS {
    
    companion object {
        private const val TAG = "PiperTTS"
        
        init {
            System.loadLibrary("piper-android")
        }
    }
    
    private var voicePtr: Long = 0
    private var speed: Float = 1.0f
    private var pitch: Float = 1.0f
    
    external fun nativeInitialize(modelPath: String, configPath: String): Long
    external fun nativeSynthesize(voicePtr: Long, text: String, speed: Float, pitch: Float): ByteArray
    external fun nativeRelease(voicePtr: Long)
    
    fun initialize(modelPath: String, configPath: String) {
        if (!File(modelPath).exists()) {
            throw IllegalArgumentException("Ses modeli bulunamadı: $modelPath")
        }
        
        Log.i(TAG, "TTS motoru başlatılıyor...")
        voicePtr = nativeInitialize(modelPath, configPath)
        
        if (voicePtr == 0L) {
            throw RuntimeException("TTS başlatılamadı!")
        }
        
        Log.i(TAG, "✅ TTS hazır (Türkçe - Cahit)")
    }
    
    fun speak(text: String, onComplete: (() -> Unit)? = null) {
        if (voicePtr == 0L) {
            Log.e(TAG, "TTS başlatılmamış!")
            return
        }
        
        Thread {
            try {
                val audioData = nativeSynthesize(voicePtr, text, speed, pitch)
                playAudio(audioData)
                onComplete?.invoke()
            } catch (e: Exception) {
                Log.e(TAG, "Synthesis hatası: ${e.message}")
            }
        }.start()
    }
    
    private fun playAudio(audioData: ByteArray) {
        // AudioTrack ile PCM ses çalma
    }
    
    fun setSpeed(newSpeed: Float) {
        speed = newSpeed.coerceIn(0.5f, 2.0f)
    }
    
    fun setPitch(newPitch: Float) {
        pitch = newPitch.coerceIn(0.5f, 2.0f)
    }
    
    fun release() {
        if (voicePtr != 0L) {
            nativeRelease(voicePtr)
            voicePtr = 0
            Log.i(TAG, "TTS kapatıldı")
        }
    }
}
