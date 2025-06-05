package com.websarva.wings.android.wearnotification.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.websarva.wings.android.wearnotification.ui.theme.*

@Composable
fun BackTextButton(
    navController: NavController,
    text: String = "← 戻る"
) {
    Text(
        text = text,
        color = nixieLightOrange,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable { navController.popBackStack() }
            .padding(8.dp)
    )
}
