package com.orlandev.chatgptlistonames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orlandev.chatgptlistonames.ui.theme.ChatGPTListoNamesTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGPTListoNamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NameListScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NameListScreen() {
    var names by remember { mutableStateOf(listOf("John", "Sarah", "David")) }
    var newName by remember { mutableStateOf("") }
    var newItemAdded by remember { mutableStateOf(false) }

    fun removeName(name: String) {
        names = names.filterNot { it == name }
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Name List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                names = names + newName
                newName = ""
                newItemAdded = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
                newItemAdded = false
            }

        },
        content = { padding ->

            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text(text = "Add new name") }
                )

                AnimatedContent(
                    targetState = names,
                    transitionSpec = {

                        //Solo para probar

                        if (newItemAdded) {
                            // If the target number is larger, it slides up and fades in
                            // while the initial (smaller) number slides up and fades out.
                            fadeIn() with fadeOut()
                        } else {
                            // If the target number is smaller, it slides down and fades in
                            // while the initial number slides down and fades out.
                            fadeIn() with  fadeOut()
                        }.using(
                            // Disable clipping since the faded slide-in/out should
                            // be displayed out of bounds.
                            SizeTransform(clip = false)
                        )

                    }
                ) { targetNames ->
                    LazyColumn {
                        items(targetNames) { name ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = name, modifier = Modifier.weight(1f).padding(4.dp))
                                IconButton(onClick = { removeName(name) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }

        }
    )
}
