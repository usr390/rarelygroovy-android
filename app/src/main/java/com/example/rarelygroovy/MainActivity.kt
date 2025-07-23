package com.example.rarelygroovy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rarelygroovy.ui.theme.RarelygroovyTheme
// Import your ViewModel
import com.example.rarelygroovy.ui.eventlist.EventListViewModel
import com.example.rarelygroovy.ui.theme.RarelygroovyTheme
// Import for viewModel()
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RarelygroovyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EventListScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EventListScreen(name: String, modifier: Modifier = Modifier, eventListViewModel: EventListViewModel = viewModel()) {
    Text(
        text = "Upcoming Events",
        modifier = modifier
    )
    // You can add a Log here to confirm EventListScreen is composed
    Log.d("EventListScreen", "EventListScreen composed, ViewModel should be active.")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RarelygroovyTheme {
        EventListScreen("Android")
    }
}