package com.example.snowstudio.data.repository

import com.example.snowstudio.data.db.*
import com.example.snowstudio.data.network.*
import com.example.snowstudio.data.settings.ApiSettings
import kotlinx.coroutines.flow.Flow


sealed class SendResult {

    data class Success(
        val reply:String
    ):SendResult()


    data class Failure(
        val message:String
    ):SendResult()

}



class ChatRepository(

    private val conversationDao:ConversationDao,

    private val messageDao:MessageDao

){



    fun observeConversations():
            Flow<List<ConversationEntity>> =
        conversationDao.observeAll()



    fun observeMessages(
        id:Long
    ):Flow<List<MessageEntity>> =
        messageDao.observeForConversation(id)




    suspend fun createConversation(
        title:String="新的对话"
    ):Long{

        return conversationDao.insert(
            ConversationEntity(
                title=title
            )
        )

    }




    suspend fun sendMessage(

        conversationId:Long,

        text:String,

        settings:ApiSettings

    ):SendResult{



        messageDao.insert(

            MessageEntity(

                conversationId=conversationId,

                role="user",

                content=text

            )

        )



        val history =
            messageDao.getForConversation(
                conversationId
            )



        val messages =
            history.map {

                ChatMessageDto(

                    role=it.role,

                    content=it.content

                )

            }.toMutableList()



        if(settings.apiKey.isBlank()){

            return SendResult.Failure(
                "请先填写 API Key"
            )

        }



        return try{


            val api =
                ApiClientFactory.getApi(
                    settings.baseUrl
                )



            val response =
                api.chatCompletion(

                    mapOf(
                        "Authorization" 
                            to "Bearer ${settings.apiKey}"
                    ),


                    ChatCompletionRequest(

                        model=settings.model,

                        messages=messages,

                        temperature=settings.temperature

                    )

                )



            if(response.isSuccessful){


                val reply =

                    response.body()
                        ?.choices
                        ?.firstOrNull()
                        ?.message
                        ?.content



                if(reply!=null){


                    messageDao.insert(

                        MessageEntity(

                            conversationId=conversationId,

                            role="assistant",

                            content=reply

                        )

                    )


                    SendResult.Success(reply)


                }else{


                    SendResult.Failure(
                        "AI没有返回内容"
                    )


                }



            }else{


                SendResult.Failure(
                    "请求失败:${response.code()}"
                )


            }



        }catch(e:Exception){


            SendResult.Failure(
                e.message ?: "未知错误"
            )

        }


    }

}