package com.petprojject.common_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.petprojject.common_ui.R
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    content: @Composable() () -> Unit,
) {
    val selectedBackground = if (isSystemInDarkTheme()) {
        R.drawable.background_dark
    } else {
        R.drawable.background_light
    }
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (hazeState != null) {
                        Modifier.hazeSource(hazeState)
                    } else {
                        Modifier
                    }
                ),
            painter = painterResource(selectedBackground),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        content()
    }
}

@Composable
@Preview
private fun BackgroundImagePreview() {
    AutoPetProjectLakhaiTheme {
        val hazeState = rememberHazeState(true)
        BackgroundImage(content = { Text("Some preview text") }, hazeState = hazeState)
    }
}