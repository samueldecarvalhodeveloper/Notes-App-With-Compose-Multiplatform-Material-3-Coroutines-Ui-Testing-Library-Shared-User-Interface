package org.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.example.notesapp.user_interface.theme.NEUTRALS_100
import org.example.notesapp.user_interface.theme.PRIMARY_500
import org.example.notesapp.user_interface.theme.SECONDARY_500
import org.example.notesapp.user_interface.theme.SECONDARY_900
import org.example.notesapp.user_interface.view_models.UserViewModel

@Composable
fun UserSignInScreen(userViewModel: UserViewModel, onUserCreated: () -> Unit) {
    var userUsername by rememberSaveable { mutableStateOf("") }
    val isUserUsernameInvalid by userViewModel.isUserUsernameInvalidState
    val isInternetErrorRisen by userViewModel.isInternetErrorRisenState

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(), backgroundColor = PRIMARY_500
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize().padding(24.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.shadow(6.dp).zIndex(1f)
                        .clip(RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp))
                        .background(NEUTRALS_100).padding(24.dp).fillMaxWidth(),
                    verticalArrangement = spacedBy(4.dp),
                ) {
                    OutlinedTextField(
                        value = userUsername,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PRIMARY_500,
                            unfocusedBorderColor = PRIMARY_500,
                            focusedLabelColor = PRIMARY_500,
                            unfocusedLabelColor = PRIMARY_500,
                        ),
                        isError = isUserUsernameInvalid || isInternetErrorRisen,
                        label = { Text(text = "Username") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = Text),
                        modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 0.dp, 14.dp),
                        onValueChange = { userUsername = it })
                    if (isUserUsernameInvalid) {
                        Text(text = "Not valid username", color = Red)
                    }

                    if (isInternetErrorRisen) {
                        Text(text = "No internet connection", color = Red)
                    }
                    Button(
                        shape = RoundedCornerShape(100),
                        colors = ButtonDefaults.buttonColors(backgroundColor = SECONDARY_500),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            userViewModel.createUser(userUsername) {
                                onUserCreated()
                            }
                        }) {
                        Text(text = "Create user", color = SECONDARY_900)
                    }
                }
            }
        }
    }
}