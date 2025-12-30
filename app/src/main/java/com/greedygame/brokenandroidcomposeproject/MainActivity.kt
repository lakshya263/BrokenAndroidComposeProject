package com.greedygame.brokenandroidcomposeproject

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.greedygame.brokenandroidcomposeproject.data.BrokenRepository
import com.greedygame.brokenandroidcomposeproject.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BrokenRepository.init(applicationContext)

//        if (com.greedygame.brokenandroidcomposeproject.BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(
//                StrictMode.ThreadPolicy.Builder()
//                    .detectNetwork()
//                    .penaltyLog()
//                    .build()
//            )
//        }

        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Broken News") }) }
                ) { padding ->
                    AppNavHost(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}