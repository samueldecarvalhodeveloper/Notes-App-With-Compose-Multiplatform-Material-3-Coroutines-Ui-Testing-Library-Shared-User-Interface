package org.example.notesapp.unitaries.user_interface.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.user_interface.components.NoteItem
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NoteItemTest {
    @Test
    fun testNoteDataIsDisplayedAndDispatchesActionOnClick() = runComposeUiTest {
        var valueToBeToggled = false

        setContent {
            NoteItem(note = NOTE_OBJECT) {
                valueToBeToggled = true
            }
        }

        onNodeWithText(NOTE_TITLE).performClick()

        assertTrue(valueToBeToggled)
    }
}
