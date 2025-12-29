package com.petprojject.common_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ChoiceItem(
    modifier: Modifier = Modifier,
    text: String,
    hazeState: HazeState,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier

            .clip(RoundedCornerShape(8.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = CarTheme.customColors.choiceCardBackground,
                    tint = HazeTint(CarTheme.customColors.choiceCardBackground),
                    blurRadius = 20.dp,
                    noiseFactor = 0.5f,
                    fallbackTint = HazeTint(CarTheme.customColors.choiceCardBackground)
                )
            )
            .border(1.dp, CarTheme.customColors.cardBorderColor, RoundedCornerShape(8.dp))
            .clickableNoIndication {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 12.dp),
            text = text
        )
    }
}

@Composable
@Preview
private fun ChoiceItemPreview() {
    AutoPetProjectLakhaiTheme {
        val hazeState= rememberHazeState(true)
        ChoiceItem(text = "Choice item", hazeState = hazeState, onClick = {})
    }
}