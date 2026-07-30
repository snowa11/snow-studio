package com.example.snowstudio.di

import android.content.Context
import com.example.snowstudio.data.db.AppDatabase
import com.example.snowstudio.data.repository.ChatRepository
import com.example.snowstudio.data.settings.SettingsStore


class AppContainer(
    context: Context
) {


    private val database =
        AppDatabase.getInstance(context)



    val settingsStore =
        SettingsStore(context)



    val chatRepository =
        ChatRepository(

            conversationDao =
                database.conversationDao(),

            messageDao =
                database.messageDao()

        )

}