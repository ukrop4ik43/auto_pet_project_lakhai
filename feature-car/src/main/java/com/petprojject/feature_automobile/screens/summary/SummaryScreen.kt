package com.petprojject.feature_automobile.screens.summary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.petprojject.common_ui.components.MainButton
import com.petprojject.common_ui.components.ScaffoldContent
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme
import com.petprojject.feature_automobile.R
import com.petprojject.common_ui.R as commonUiR

@Composable
fun SummaryScreen(
    uiState: SummaryContract.UiState,
    onAction: (SummaryContract.UiAction) -> Unit,
) {
    Scaffold(
        Modifier
            .fillMaxSize(),
        containerColor = CarTheme.customColors.backgroundColor,
        topBar = {
            Box(Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .statusBarsPadding()) {
                Text(
                    modifier = Modifier

                        .align(Alignment.Center),
                    text = stringResource(R.string.summary),
                    style = TextStyle(fontSize = 24.sp)
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterStart)
                        .clickableNoIndication {
                            onAction(SummaryContract.UiAction.OnBackClick)
                        },
                    painter = painterResource(commonUiR.drawable.arrow_back),
                    tint = CarTheme.customColors.iconColor,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        ScaffoldContent(
            isLoading = uiState.isLoading, error =
                uiState.error, paddingValues = padding, content = {
                Column(
                    modifier = Modifier.padding(padding),
                ) {
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
                            text = "${R.string.manufacturer}: ${uiState.manufacturer.second}",
                            autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                            style = TextStyle(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
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
                            text = "${stringResource(R.string.model)}: ${uiState.model.second}",
                            autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                            style = TextStyle(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
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
                            text = "${stringResource(R.string.year)}: ${uiState.year.second}",
                            autoSize = TextAutoSize.StepBased(maxFontSize = 20.sp),
                            style = TextStyle(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 6.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.finish),
                        onClick = {
                            onAction(SummaryContract.UiAction.OnFinishClick)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            })
    }
}

@Composable
@Preview
private fun SummaryScreenPreview() {
    AutoPetProjectLakhaiTheme {
        SummaryScreen(
            onAction = {}, uiState = SummaryContract.UiState(
                model = "" to "",
                manufacturer = "" to "",
                year = "" to ""
            )
        )
    }
}