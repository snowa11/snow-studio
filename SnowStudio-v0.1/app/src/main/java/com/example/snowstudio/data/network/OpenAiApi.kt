package com.example.snowstudio.data.network


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface OpenAiApi {


    @POST("v1/chat/completions")
    suspend fun chatCompletion(

        @HeaderMap headers:Map<String,String>,

        @Body request:ChatCompletionRequest

    ):Response<ChatCompletionResponse>

}