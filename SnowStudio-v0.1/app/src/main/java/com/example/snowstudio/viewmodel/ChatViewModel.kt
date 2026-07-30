package com.example.snowstudio.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snowstudio.data.db.MessageEntity
import com.example.snowstudio.data.repository.*
import com.example.snowstudio.data.settings.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class ChatViewModel(

    private val repository:ChatRepository,

    private val settingsStore:SettingsStore

):ViewModel(){



    private val _messages =
        MutableStateFlow<List<MessageEntity>>(
            emptyList()
        )


    val messages =
        _messages.asStateFlow()



    private val _isSending =
        MutableStateFlow(false)


    val isSending =
        _isSending.asStateFlow()



    private val _errorMessage =
        MutableStateFlow<String?>(null)


    val errorMessage =
        _errorMessage.asStateFlow()



    val settings =
        settingsStore.settings




    private var currentConversation:Long = 1L




    fun observeConversation(
        id:Long
    ){

        currentConversation=id


        viewModelScope.launch{


            repository
                .observeMessages(id)
                .collect{

                    _messages.value=it

                }

        }

    }




    fun sendMessage(
        text:String
    ){

        viewModelScope.launch{


            _isSending.value=true


            val s =
                settings.first()



            when(
                val result =
                    repository.sendMessage(
                        currentConversation,
                        text,
                        s
                    )
            ){

                is SendResult.Failure ->

                    _errorMessage.value =
                        result.message


                is SendResult.Success -> {}

            }


            _isSending.value=false

        }

    }




    fun clearError(){

        _errorMessage.value=null

    }

}