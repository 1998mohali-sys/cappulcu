package com.cappulcu.assistant

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoryDatabase(context: Context) : SQLiteOpenHelper(context, "cappulcu.db", null, 1) {
    
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE conversations (id INTEGER PRIMARY KEY, user_message TEXT, assistant_message TEXT, timestamp INTEGER)")
        db.execSQL("CREATE TABLE reminders (id INTEGER PRIMARY KEY, title TEXT, trigger_time INTEGER)")
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    
    fun saveConversation(userMessage: String, assistantMessage: String, timestamp: Long) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_message", userMessage)
            put("assistant_message", assistantMessage)
            put("timestamp", timestamp)
        }
        db.insert("conversations", null, values)
    }
    
    fun getUpcomingReminders(hoursAhead: Int): List<String> {
        return listOf() // Şimdilik boş
    }
}
