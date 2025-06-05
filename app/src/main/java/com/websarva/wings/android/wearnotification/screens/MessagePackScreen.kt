package com.websarva.wings.android.wearnotification.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.websarva.wings.android.wearnotification.R
import com.websarva.wings.android.wearnotification.ui.BackTextButton
import com.websarva.wings.android.wearnotification.ui.NixieButton
import com.websarva.wings.android.wearnotification.ui.ScreenButton
import com.websarva.wings.android.wearnotification.viewmodel.HomeViewModel

@Composable
fun MessagePackScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item{
            BackTextButton(navController = navController)
        }

        item{
            NixieButton(
                onClick = {  },
                buttonText = stringResource(R.string.button_message_work_alert)
            )
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{
            NixieButton(
                onClick = {  },
                buttonText = stringResource(R.string.button_message_machine_alert)
            )
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{
            NixieButton(
                onClick = {  },
                buttonText = stringResource(R.string.button_message_break)
            )
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{
            NixieButton(
                onClick = {  },
                buttonText = stringResource(R.string.button_message_toilet)
            )
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ScreenButton(
                navController = navController,
                navigateRoute=Screens.MESSAGE.name,
                buttonText = stringResource(R.string.button_title_chat)
            )
        }
    }
}