package com.petprojject.feature_ai.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.components.MainButton
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun AiResponseContainer(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = CarTheme.customColors.aiResponseBackgroundColor,
                    tint = HazeTint(CarTheme.customColors.aiResponseBackgroundColor),
                    blurRadius = 16.dp,
                    noiseFactor = 0.3f,
                    fallbackTint = HazeTint(CarTheme.customColors.aiResponseBackgroundColor)
                )
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
@Preview
private fun AiResponseContainerPreview() {
    AutoPetProjectLakhaiTheme {
        val hazeState = rememberHazeState(true)
        AiResponseContainer(content={Text("Hello world")}, hazeState = hazeState)
    }
}