package org.example.notesapp.unitaries.user_interface.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.TEXT_INPUT_PLACEHOLDER_TEXT
import org.example.notesapp.user_interface.components.TextInput
import org.example.notesapp.user_interface.theme.NEUTRALS_300
import org.example.notesapp.user_interface.theme.NEUTRALS_900
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class TextInputTest {
    @Test
    fun testIfActionIsDispatchedOnTextChange() = runComposeUiTest {
        var valueToBeToggled = false

        setContent {
            TextInput(
                value = "",
                placeholder = TEXT_INPUT_PLACEHOLDER_TEXT,
                singleLine = false,
                textColor = NEUTRALS_900,
                placeholderColor = NEUTRALS_300,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                    .fillMaxSize(),
                onValueChange = {
                    valueToBeToggled = !valueToBeToggled
                }
            )
        }

        onNodeWithText(TEXT_INPUT_PLACEHOLDER_TEXT).performTextInput(NOTE_TITLE)

        assertTrue(valueToBeToggled)
    }
}