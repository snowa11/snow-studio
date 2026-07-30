package com.example.snowstudio.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String = "新的对话",

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()

)