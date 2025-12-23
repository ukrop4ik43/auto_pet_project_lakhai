package com.petprojject.feature_automobile.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.components.MainButton
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.feature_automobile.R

@Composable
fun StartScreen(
    uiState: StartContract.UiState,
    onAction: (StartContract.UiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()
            .background(CarTheme.customColors.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainButton(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.choose_your_car),
            onClick = {
                onAction(StartContract.UiAction.OnChooseYourCarClick)
            }
        )
        Spacer(Modifier.height(12.dp))
        MainButton(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.history),
            onClick = {
                onAction(StartContract.UiAction.OnHistoryClick)
            }
        )
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    AutoPetProjectLakhaiTheme {
        StartScreen(
            onAction = {}, uiState = StartContract.UiState()
        )
    }
}