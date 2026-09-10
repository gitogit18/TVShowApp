package com.example.tvshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tvshowapp.model.RetrofitInstance
import com.example.tvshowapp.model.ShowRepository
import com.example.tvshowapp.view.AppNavigation
import com.example.tvshowapp.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val repository = ShowRepository(RetrofitInstance.api)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                AppNavigation()
        }
    }
}
