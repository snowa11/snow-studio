import com.example.snowstudio.data.db.MessageEntity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.snowstudio.viewmodel.ChatViewModel


@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {

    val messages by viewModel.messages.collectAsState()

    var input by remember {
        mutableStateOf("")
    }


    Scaffold(

        bottomBar = {

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                TextField(

                    value=input,

                    onValueChange={
                        input=it
                    },

                    modifier =
                        Modifier.weight(1f),

                    placeholder={
                        Text("输入消息")
                    }

                )


                IconButton(

                    onClick={

                        if(input.isNotBlank()){

                            viewModel.sendMessage(input)

                            input=""

                        }

                    }

                ){

                    Icon(
                        Icons.Default.Send,
                        contentDescription=null
                    )

                }

            }

        }

    ){ padding ->


        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),

            contentPadding =
                PaddingValues(12.dp)

        ){

            items(messages){

                MessageBubble(it)

            }

        }


    }

}



@Composable
fun MessageBubble(
    message:MessageEntity
){

    Card(

        modifier =
            Modifier
                .padding(6.dp)
                .fillMaxWidth(),

        shape =
            RoundedCornerShape(16.dp)

    ){

        Column(
            modifier =
                Modifier.padding(12.dp)
        ){

            Text(
                text =
                    if(message.role=="user")
                        "我"
                    else
                        "AI"
            )


            Text(
                text =
                    message.content
            )

        }

    }

}
