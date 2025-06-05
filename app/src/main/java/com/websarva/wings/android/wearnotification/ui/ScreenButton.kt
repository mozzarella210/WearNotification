package com.websarva.wings.android.wearnotification.ui

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.websarva.wings.android.wearnotification.ui.theme.*

@Composable
fun ScreenButton(
    navController: NavController,
    buttonText: String,
    navigateRoute: String
) {

    Button(
        onClick = { navController.navigate(navigateRoute)},
        modifier = Modifier
            .width(140.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = nixieLightOrange),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = buttonText,
            color = Color.Black
        )
    }
}
