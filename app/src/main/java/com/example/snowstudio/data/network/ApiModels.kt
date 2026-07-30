package com.example.snowstudio.data.network


data class ChatMessageDto(
    val role:String,
    val content:String
)


data class ChatCompletionRequest(
    val model:String,
    val messages:List<ChatMessageDto>,
    val temperature:Float = 0.7f,
    val stream:Boolean = false
)


data class ChatCompletionResponse(
    val id:String? = null,
    val choices:List<Choice> = emptyList(),
    val error:ApiError? = null
)


data class Choice(
    val index:Int? = null,
    val message:ChatMessageDto? = null,
    val finish_reason:String? = null
)


data class ApiError(
    val message:String? = null,
    val type:String? = null,
    val code:String? = null
)