package com.example.snowstudio.data.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClientFactory {


    @Volatile
    private var cachedUrl:String? = null


    @Volatile
    private var cachedApi:OpenAiApi? = null



    fun getApi(baseUrl:String):OpenAiApi{


        val url =
            if(baseUrl.endsWith("/"))
                baseUrl
            else
                "$baseUrl/"



        if(
            cachedApi != null &&
            cachedUrl == url
        ){
            return cachedApi!!
        }



        synchronized(this){


            val logger =
                HttpLoggingInterceptor()
                    .apply {
                        level =
                            HttpLoggingInterceptor.Level.BASIC
                    }



            val client =
                OkHttpClient.Builder()
                    .connectTimeout(
                        30,
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        60,
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        60,
                        TimeUnit.SECONDS
                    )
                    .addInterceptor(logger)
                    .build()



            val retrofit =
                Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    )
                    .build()



            val api =
                retrofit.create(
                    OpenAiApi::class.java
                )


            cachedUrl=url
            cachedApi=api


            return api

        }

    }

}