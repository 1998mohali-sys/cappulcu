package com.cappulcu.assistant

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val statusText = findViewById<TextView>(R.id.statusText)
        val activateButton = findViewById<Button>(R.id.activateButton)
        
        statusText.text = "Çapulcu hazır!"
        activateButton.text = "Başlat"
    }
}
