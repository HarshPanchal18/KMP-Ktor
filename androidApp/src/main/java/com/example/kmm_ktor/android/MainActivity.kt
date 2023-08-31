package com.example.kmm_ktor.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.kmm_ktor.Greeting
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val mainScope: CoroutineScope = MainScope()
    private val greeting = Greeting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    HomeUI()
                }
            }
        }
    }

    @Composable
    fun HomeUI() {
        var text by remember { mutableStateOf("Loading...") }

        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
        LaunchedEffect(Unit) {
            mainScope.launch {
                kotlin.runCatching { greeting.greeting() }
                    .onSuccess { text = it }
                    .onFailure { text = it.message.toString() }
                Toast.makeText(this@MainActivity, "hello2", Toast.LENGTH_LONG).show()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text, fontSize = 20.sp)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
