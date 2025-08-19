package org.example.notesapp.user_interface.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import org.example.notesapp.user_interface.theme.NEUTRALS_100
import org.example.notesapp.user_interface.theme.PRIMARY_500

@Composable
fun GreetingAppTopBar(userUsername: String) {
    TopAppBar(
        backgroundColor = PRIMARY_500,
        contentColor = NEUTRALS_100,
        title = { Text(text = TOP_BAR_GREETING_TITLE_TEXT(userUsername)) }
    )
}