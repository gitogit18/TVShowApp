package com.example.tvshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tvshowapp.model.RetrofitInstance
import com.example.tvshowapp.model.ShowRepository
import com.example.tvshowapp.ui.theme.TVShowAppTheme
import com.example.tvshowapp.view.HomeScreen
import com.example.tvshowapp.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val repository = ShowRepository(RetrofitInstance.api)
    private val viewModel = HomeViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TVShowAppTheme {
                HomeScreen(
                    uiState = viewModel.uiState,

                    onShowClick = { id ->
                        // Navigation
                    },

                    onRetry = {
                        viewModel.loadShows()
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TVShowAppTheme {
        Greeting("Android")
    }
}