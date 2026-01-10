package com.growfolio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.growfolio.app.presentation.AuthState
import com.growfolio.app.presentation.MainScreen
import com.growfolio.app.presentation.RootViewModel
import com.growfolio.app.presentation.auth.LoginScreen
import com.growfolio.app.ui.theme.GrowfolioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowfolioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: RootViewModel = hiltViewModel()
                    val authState by viewModel.authState.collectAsState()
                    
                    when (authState) {
                        is AuthState.Loading -> {
                            // Optionally show a splash screen or loader
                        }
                        is AuthState.Authenticated -> {
                            MainScreen()
                        }
                        is AuthState.Unauthenticated -> {
                            LoginScreen()
                        }
                    }
                }
            }
        }
    }
}
