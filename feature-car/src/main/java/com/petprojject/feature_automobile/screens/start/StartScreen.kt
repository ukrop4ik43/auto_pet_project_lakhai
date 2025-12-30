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
import com.petprojject.common_ui.components.BackgroundImage
import com.petprojject.common_ui.components.MainButton
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.feature_automobile.R
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun StartScreen(
    uiState: StartContract.UiState,
    onAction: (StartContract.UiAction) -> Unit,
) {
    val hazeState = rememberHazeState(true)
    BackgroundImage(hazeState=hazeState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .systemBarsPadding(),
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
                }, hazeState = hazeState
            )
            Spacer(Modifier.height(4.dp))
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.history),
                onClick = {
                    onAction(StartContract.UiAction.OnHistoryClick)
                }, hazeState = hazeState
            )
            Spacer(Modifier.height(4.dp))
            MainButton(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.ai),
                onClick = {
                    onAction(StartContract.UiAction.OnAiClick)
                }, hazeState = hazeState
            )
        }
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