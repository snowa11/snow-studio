package com.example.snowstudio


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snowstudio.di.AppContainer
import com.example.snowstudio.ui.ChatScreen
import com.example.snowstudio.ui.theme.SnowStudioTheme
import com.example.snowstudio.viewmodel.ChatViewModel



class MainActivity : ComponentActivity(){


    private lateinit var container:AppContainer



    override fun onCreate(
        savedInstanceState: Bundle?
    ){

        super.onCreate(savedInstanceState)



        container =
            AppContainer(this)



        setContent{


            SnowStudioTheme{


                val vm:ChatViewModel =
                    viewModel(
                        factory =
                        object:ViewModelProvider.Factory{


                            override fun <T:ViewModel>
                            create(
                                modelClass:Class<T>
                            ):T{


                                return ChatViewModel(

                                    container.chatRepository,

                                    container.settingsStore

                                ) as T

                            }


                        }
                    )



                ChatScreen(
                    viewModel = vm
                )


            }

        }

    }

}