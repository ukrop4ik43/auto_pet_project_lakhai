package com.petprojject.autolakhai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowInsetsControllerCompat
import com.petprojject.autolakhai.ui.navigation.NavHost
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true
        setContent {
            AutoPetProjectLakhaiTheme {
                NavHost()
            }
        }
    }
}

