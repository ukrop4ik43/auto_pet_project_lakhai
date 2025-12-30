package com.petprojject.feature_automobile.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.common_ui.components.BackgroundImage
import com.petprojject.common_ui.components.MainButton
import com.petprojject.feature_automobile.R
import com.petprojject.common_ui.R as commonUiR
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.domain.car.model.CarHistoryItem
import com.petprojject.feature_automobile.components.CarHistoryItemComponent
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HistoryScreen(
    uiState: HistoryContract.UiState,
    onAction: (HistoryContract.UiAction) -> Unit,
) {
    val hazeState = rememberHazeState()
    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .statusBarsPadding()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.history),
                    style = TextStyle(fontSize = 24.sp),
                    color = CarTheme.customColors.textColor
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(HistoryContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading,
            error = uiState.error,
            paddingValues = padding,
            onTryAgain = {
                onAction(HistoryContract.UiAction.TryAgain)
            },
            content = {
                BackgroundImage(hazeState = hazeState) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                    ) {
                        LazyColumn(Modifier.fillMaxSize()) {
                            itemsIndexed(uiState.listOfHistory) { index, item ->
                                CarHistoryItemComponent(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .fillMaxWidth(),
                                    car = item,
                                    onItemClick = {
                                        onAction(HistoryContract.UiAction.OnItemClick(item))
                                    },
                                    onItemDelete = {
                                        onAction(HistoryContract.UiAction.DeleteItem(item))
                                    }
                                )
                                if (index != uiState.listOfHistory.size) {
                                    HorizontalDivider(color = CarTheme.customColors.cardBorderColor)
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                        MainButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.clear),
                            onClick = {
                                onAction(HistoryContract.UiAction.OnClearClick)
                            }, hazeState = hazeState
                        )
                    }
                }
            })
    }
}

@Composable
@Preview
private fun HistoryScreenPreview() {
    AutoPetProjectLakhaiTheme {
        HistoryScreen(
            uiState = HistoryContract.UiState(
                listOfHistory = listOf(
                    CarHistoryItem(
                        id = 1L,
                        manufacturer = "Toyota",
                        model = "Corolla",
                        year = "2018"
                    ),
                    CarHistoryItem(
                        id = 2L,
                        manufacturer = "BMW",
                        model = "X5",
                        year = "2020"
                    ),
                    CarHistoryItem(
                        id = 3L,
                        manufacturer = "Audi",
                        model = "A4",
                        year = "2019"
                    ),
                    CarHistoryItem(
                        id = 4L,
                        manufacturer = "Tesla",
                        model = "Model 3",
                        year = "2021"
                    ),
                    CarHistoryItem(
                        id = 5L,
                        manufacturer = "Volkswagen",
                        model = "Golf",
                        year = "2017"
                    )
                )
            ), onAction = {})
    }
}