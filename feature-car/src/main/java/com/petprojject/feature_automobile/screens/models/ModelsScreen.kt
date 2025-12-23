package com.petprojject.feature_automobile.screens.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.feature_automobile.R
import com.petprojject.common_ui.R as commonUiR
import com.petprojject.common_ui.components.ChoiceItem
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme

@Composable
fun ModelsScreen(
    uiState: ModelsContract.UiState,
    onAction: (ModelsContract.UiAction) -> Unit,
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        topBar = {
            Box(Modifier.fillMaxWidth().padding(top=8.dp)) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.choose_model) + ":",
                    style = TextStyle(fontSize = 32.sp)
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(ModelsContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        },
        containerColor = CarTheme.customColors.backgroundColor,
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading, error =
                uiState.error, paddingValues = padding, onTryAgain = {
                onAction(ModelsContract.UiAction.TryAgain)
            }, content = {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            border = BorderStroke(2.dp, CarTheme.customColors.cardBorderColor),
                            colors = CardDefaults.cardColors(containerColor = CarTheme.customColors.resultCardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                        ) {
                            BasicText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = "${stringResource(R.string.manufacturer) + ":"} ${uiState.manufacturer.second}",
                                autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                                style = TextStyle(fontSize = 20.sp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    item {
                        TextField(
                            value = uiState.searchText,
                            onValueChange = { onAction(ModelsContract.UiAction.OnSearchTextChange(it)) },
                            label = { Text(stringResource(R.string.enter_model)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp),
                            singleLine = true,
                        )
                    }
                    items(uiState.modelsMapForShow.toList()) { item ->
                        ChoiceItem(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            text = item.second,
                            onClick = {
                                onAction(ModelsContract.UiAction.OnModelClick(item))
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            })
    }
}

@Composable
@Preview
private fun ModelsScreenPreview() {
    AutoPetProjectLakhaiTheme {
        ModelsScreen(
            onAction = {}, uiState = ModelsContract.UiState(
                originalModelsMap = emptyMap(),
                manufacturer = "" to "",
                modelsMapForShow = emptyMap()
            )
        )
    }
}