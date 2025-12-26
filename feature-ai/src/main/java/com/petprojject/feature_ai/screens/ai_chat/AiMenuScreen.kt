package com.petprojject.feature_ai.screens.ai_chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.common_ui.components.MainButton
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.feature_ai.R
import com.petprojject.common_ui.R as commonUiR

@Composable
fun AiMenuScreen(
    uiState: AiMenuContract.UiState,
    onAction: (AiMenuContract.UiAction) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(CarTheme.customColors.backgroundColor)
            .navigationBarsPadding()
            .systemBarsPadding(), contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.powered_by) + " ",
                    style = TextStyle(fontSize = 24.sp),
                    color = CarTheme.customColors.textColor
                )
                Image(
                    painter = painterResource(R.drawable.gemini),
                    modifier = Modifier.height(24.dp),
                    contentDescription = null
                )
            }
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterStart)
                    .clickableNoIndication {
                        onAction(AiMenuContract.UiAction.OnBackClick)
                    },
                painter = painterResource(commonUiR.drawable.arrow_back),
                tint = CarTheme.customColors.iconColor,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.show_choice_alternatives),
                onClick = {
                    onAction(AiMenuContract.UiAction.OnShowChoiceAlternativesScreen)
                })
            Spacer(Modifier.height(12.dp))
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.generate_conclusion),
                onClick = {
                    onAction(AiMenuContract.UiAction.OnGenerateConclusionClick)
                })
            Spacer(Modifier.height(12.dp))
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(), text = stringResource(R.string.compare_choices), onClick = {
                    onAction(AiMenuContract.UiAction.OnCompareChoicesClick)
                })
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp)
                .padding(vertical = 12.dp),
            text = stringResource(R.string.ai_can_be_wrong),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = CarTheme.customColors.descriptionColor
        )
    }
}

@Composable
@Preview
private fun AiMenuScreenPreview() {
    AutoPetProjectLakhaiTheme {
        AiMenuScreen(uiState = AiMenuContract.UiState(), onAction = {})
    }
}