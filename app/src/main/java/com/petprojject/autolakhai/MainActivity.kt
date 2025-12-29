package com.petprojject.autolakhai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.petprojject.autolakhai.ui.navigation.NavHost
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoPetProjectLakhaiTheme {
                NavHost()
            }
        }
    }
}

