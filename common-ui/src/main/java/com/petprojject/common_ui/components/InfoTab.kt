package com.petprojject.common_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petprojject.common_ui.theme.AutoPetProjectLakhaiTheme
import com.petprojject.common_ui.theme.CarTheme

@Composable
fun InfoTab(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = CarTheme.customColors.resultCardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = 20.sp
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                )
            )
        }
    }
}


@Composable
@Preview
private fun InfoTabPreview() {
    AutoPetProjectLakhaiTheme() {
        InfoTab(text = "Some text")
    }
}