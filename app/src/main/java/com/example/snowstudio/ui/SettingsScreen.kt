package com.example.snowstudio.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.snowstudio.data.settings.ApiSettings
import com.example.snowstudio.viewmodel.ChatViewModel


@Composable
fun SettingsScreen(
    viewModel:ChatViewModel
){

    val settings by
    viewModel.settings.collectAsState(
        initial = ApiSettings()
    )


    var url by remember {
        mutableStateOf(settings.baseUrl)
    }

    var key by remember {
        mutableStateOf(settings.apiKey)
    }


    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)

    ){


        Text(
            "Snow Studio 设置"
        )


        Spacer(
            Modifier.height(12.dp)
        )


        TextField(

            value=url,

            onValueChange={
                url=it
            },

            label={
                Text("Base URL")
            }

        )


        Spacer(
            Modifier.height(8.dp)
        )


        TextField(

            value=key,

            onValueChange={
                key=it
            },

            label={
                Text("API Key")
            }

        )


        Button(

            onClick={

                // 保存功能下一版完善

            }

        ){

            Text("保存")

        }

    }

}