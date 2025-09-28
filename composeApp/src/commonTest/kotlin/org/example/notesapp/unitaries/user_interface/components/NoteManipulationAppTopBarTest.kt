package org.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.user_interface.components.NoteManipulationAppTopBar
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NoteManipulationAppTopBarTest {
    @Test
    fun testIfButtonsDispatchesActionsOnClick() = runComposeUiTest {
        var firstValueToBeToggled = false
        var secondValueToBeToggled = false
        var thirdValueToBeToggled = false
        var fourthValueToBeToggled = false

        setContent {
            NoteManipulationAppTopBar(
                isManipulateNoteButtonAble = true,
                isConcludeNoteButtonAble = true,
                isDeleteNoteButtonAble = true,
                onNavigationIconButtonClick = {
                    firstValueToBeToggled = true
                },
                onEditNoteIconButtonClick = {
                    secondValueToBeToggled = true
                },
                onDeleteNoteIconButtonClick = {
                    thirdValueToBeToggled = true
                },
                onConcludeNoteIconButtonClick = {
                    fourthValueToBeToggled = true
                }
            )
        }

        onNodeWithContentDescription(BACK_BUTTON_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        onNodeWithContentDescription(EDIT_NOTE_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        onNodeWithContentDescription(DELETE_NOTE_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()
        onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION)
            .assertIsDisplayed().performClick()

        assertTrue(firstValueToBeToggled)
        assertTrue(secondValueToBeToggled)
        assertTrue(thirdValueToBeToggled)
        assertTrue(fourthValueToBeToggled)
    }
}