package com.petprojject.feature_automobile.screens.manufacturers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.common_ui.components.ChoiceItem
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme

@Composable
fun ManufacturersScreen(
    uiState: ManufacturersContract.UiState,
    onAction: (ManufacturersContract.UiAction) -> Unit,
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp),
                text = "Choose manufacturer:",
                style = TextStyle(fontSize = 32.sp)
            )
        },
        containerColor = CarTheme.customColors.backgroundColor,
    ) { padding ->

        ScaffoldContent(
            isLoading = uiState.isLoading, error =
                uiState.error, paddingValues = padding,
            onTryAgain = {
                onAction(ManufacturersContract.UiAction.TryAgain)
            }, content = {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                ) {
                    items(uiState.manufacturersMap.toList()) { item ->
                        ChoiceItem(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            text = item.second,
                            onClick = {
                                onAction(
                                    ManufacturersContract.UiAction.OnManufacturerClick(
                                        item
                                    )
                                )
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        onAction(ManufacturersContract.UiAction.OnBottomReached)
                    }
                }
            })
    }
}

@Composable
@Preview
private fun ManufacturerScreenPreview() {
    AutoPetProjectLakhaiTheme {
        ManufacturersScreen(
            onAction = {}, uiState = ManufacturersContract.UiState(manufacturersMap = mapOf())
        )
    }
}