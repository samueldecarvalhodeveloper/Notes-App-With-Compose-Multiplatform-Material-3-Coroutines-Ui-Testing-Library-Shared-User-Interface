package org.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import org.example.notesapp.user_interface.components.GreetingAppTopBar
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class GreetingAppTopBarTest {
    @Test
    fun testIfGreetingWithUserUsernameIsDisplayed() = runComposeUiTest {
        setContent {
            GreetingAppTopBar(userUsername = USER_USERNAME)
        }

        onNodeWithText(TOP_BAR_GREETING_TITLE_TEXT(USER_USERNAME)).assertIsDisplayed()
    }
}