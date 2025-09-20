package org.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.user_interface.components.CreateNoteFloatingActionButton
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class CreateNoteFloatingActionButtonTest {
    @Test
    fun testIfButtonIsDisplayedAndDispatchesActionOnClick() = runComposeUiTest {
        var valueToBeToggled = false

        setContent {
            CreateNoteFloatingActionButton {
                valueToBeToggled = true
            }
        }

        onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .performClick()

        assertTrue(valueToBeToggled)
    }
}