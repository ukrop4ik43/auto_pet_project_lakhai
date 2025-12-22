package com.petprojject.common_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.modifiers.clickableNoIndication
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme

@Composable
fun MainButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .clickableNoIndication {
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, CarTheme.customColors.cardBorderColor),
        colors = CardDefaults.cardColors(containerColor = CarTheme.customColors.mainButtonBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(vertical = 12.dp),
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun MainButtonreview() {
    AutoPetProjectLakhaiTheme {
        MainButton(text = "Main button", onClick = {})
    }
}