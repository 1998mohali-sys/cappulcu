package com.cappulcu.assistant

import android.util.Log
import java.io.File

class LlamaEngine {
    
    companion object {
        private const val TAG = "LlamaEngine"
        
        init {
            System.loadLibrary("llama-android")
        }
    }
    
    private var modelPtr: Long = 0
    private var contextPtr: Long = 0
    private var systemPrompt: String = ""
    
    external fun nativeLoadModel(modelPath: String, contextSize: Int, gpuLayers: Int): Long
    external fun nativeGenerate(contextPtr: Long, prompt: String, maxTokens: Int, 
                                temperature: Float, topP: Float, stopSequences: Array<String>): String
    external fun nativeGetEmbedding(contextPtr: Long, text: String): FloatArray
    external fun nativeUnloadModel(modelPtr: Long)
    external fun nativeSetSystemPrompt(contextPtr: Long, prompt: String)
    
    fun loadModel(modelPath: String, contextSize: Int = 4096, gpuLayers: Int = 20) {
        if (!File(modelPath).exists()) {
            throw IllegalArgumentException("Model dosyası bulunamadı: $modelPath")
        }
        
        Log.i(TAG, "Model yükleniyor: $modelPath")
        modelPtr = nativeLoadModel(modelPath, contextSize, gpuLayers)
        
        if (modelPtr == 0L) {
            throw RuntimeException("Model yüklenemedi!")
        }
        
        Log.i(TAG, "✅ Model başarıyla yüklendi")
    }
    
    fun generate(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, 
                 topP: Float = 0.9f, stopSequences: List<String> = emptyList()): String {
        if (modelPtr == 0L) {
            throw IllegalStateException("Model yüklenmemiş!")
        }
        
        return nativeGenerate(contextPtr, prompt, maxTokens, temperature, topP, stopSequences.toTypedArray())
    }
    
    fun getEmbedding(text: String): FloatArray {
        return nativeGetEmbedding(contextPtr, text)
    }
    
    fun setSystemPrompt(prompt: String) {
        systemPrompt = prompt
        nativeSetSystemPrompt(contextPtr, prompt)
    }
    
    fun getSystemPrompt(): String = systemPrompt
    
    fun unloadModel() {
        if (modelPtr != 0L) {
            nativeUnloadModel(modelPtr)
            modelPtr = 0
            Log.i(TAG, "Model kaldırıldı")
        }
    }
}
