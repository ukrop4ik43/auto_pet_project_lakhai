package com.petprojject.feature_automobile.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.feature_automobile.screens.manufacturers.ManufacturersContract

@Composable
fun HistoryScreen(
    uiState: HistoryContract.UiState,
    onAction: (HistoryContract.UiAction) -> Unit,
) {

}

@Composable
@Preview
private fun HistoryScreenPreview() {
    AutoPetProjectLakhaiTheme {
        HistoryScreen(uiState = HistoryContract.UiState(), onAction = {})
    }
}