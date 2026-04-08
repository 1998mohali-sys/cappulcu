package com.cappulcu.assistant

import android.util.Log

class WhisperEngine {
    
    companion object {
        private const val TAG = "WhisperEngine"
        
        init {
            System.loadLibrary("whisper-android")
        }
    }
    
    private var ctxPtr: Long = 0
    
    external fun nativeInit(modelPath: String): Long
    external fun nativeTranscribe(ctxPtr: Long, audioData: FloatArray, language: String): String
    external fun nativeFree(ctxPtr: Long)
    
    fun initialize(modelPath: String) {
        Log.i(TAG, "Whisper başlatılıyor: $modelPath")
        ctxPtr = nativeInit(modelPath)
        if (ctxPtr == 0L) {
            throw RuntimeException("Whisper başlatılamadı!")
        }
        Log.i(TAG, "✅ Whisper hazır")
    }
    
    fun transcribe(audioData: FloatArray, language: String = "tr"): String {
        if (ctxPtr == 0L) {
            throw IllegalStateException("Whisper başlatılmamış!")
        }
        
        val startTime = System.currentTimeMillis()
        val text = nativeTranscribe(ctxPtr, audioData, language)
        val duration = System.currentTimeMillis() - startTime
        
        Log.i(TAG, "Transcription: ${text.take(30)}... (${duration}ms)")
        return text
    }
    
    fun release() {
        if (ctxPtr != 0L) {
            nativeFree(ctxPtr)
            ctxPtr = 0
            Log.i(TAG, "Whisper kapatıldı")
        }
    }
}
