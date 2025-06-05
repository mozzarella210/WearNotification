package com.websarva.wings.android.wearnotification.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.wearnotification.ui.theme.*

@Composable
fun NixieButton(
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = nixieOrange,
            contentColor = Color.Black
        )
    ) {
        Text(buttonText)
    }
}
