package com.example.snowstudio.data.settings


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(
    name="settings"
)



data class ApiSettings(

    val baseUrl:String =
        "https://api.openai.com/",

    val apiKey:String = "",

    val model:String =
        "gpt-4o-mini",

    val temperature:Float = 0.7f,

    val systemPrompt:String = ""

)



class SettingsStore(
    private val context:Context
){


    private object Keys{

        val URL =
            stringPreferencesKey("url")

        val KEY =
            stringPreferencesKey("key")

        val MODEL =
            stringPreferencesKey("model")

        val TEMP =
            stringPreferencesKey("temperature")

        val PROMPT =
            stringPreferencesKey("prompt")

    }



    val settings:Flow<ApiSettings> =
        context.dataStore.data.map { p ->

            ApiSettings(

                baseUrl =
                    p[Keys.URL]
                        ?: "https://api.openai.com/",

                apiKey =
                    p[Keys.KEY]
                        ?: "",

                model =
                    p[Keys.MODEL]
                        ?: "gpt-4o-mini",

                temperature =
                    p[Keys.TEMP]
                        ?.toFloatOrNull()
                        ?:0.7f,

                systemPrompt =
                    p[Keys.PROMPT]
                        ?: ""

            )

        }



    suspend fun save(
        s:ApiSettings
    ){

        context.dataStore.edit { p ->


            p[Keys.URL]=s.baseUrl

            p[Keys.KEY]=s.apiKey

            p[Keys.MODEL]=s.model

            p[Keys.TEMP]=s.temperature.toString()

            p[Keys.PROMPT]=s.systemPrompt


        }

    }

}