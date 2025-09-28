package org.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.user_interface.sections.ListOfNotesSection
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ListOfNotesSectionTest {
    @Test
    fun testIfListOfNotesIsDisplayedAndItemsAreClickable() = runComposeUiTest {
        var valueToBeToggled = false

        setContent {
            ListOfNotesSection(listOfNotes = listOf(NOTE_OBJECT), onNoteItemClick = {
                valueToBeToggled = !valueToBeToggled
            })
        }

        onNodeWithText(NOTE_TITLE).assertIsDisplayed().performClick()

        assertTrue(valueToBeToggled)
    }
}