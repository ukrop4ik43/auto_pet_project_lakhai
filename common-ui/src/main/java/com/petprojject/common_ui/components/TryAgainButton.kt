package com.petprojject.common_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme


@Composable
fun TryAgainSection(modifier: Modifier = Modifier, error: String, onClick: () -> Unit) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(60.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = "Error: $error",
            style = MaterialTheme.typography.headlineSmall,
            color = CarTheme.customColors.textColor,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CarTheme.customColors.tryAgainButtonContainer),
            onClick = {
                onClick()
            },
        ) {
            Text(
                text = "Try again",
                style = MaterialTheme.typography.headlineSmall,
                color = CarTheme.customColors.textColor
            )
        }
    }
}

@Preview
@Composable
private fun TryAgainSectionPreview() {
    AutoPetProjectLakhaiTheme {
        TryAgainSection(modifier = Modifier.fillMaxWidth(), error = "Some error!", onClick = {})
    }
}