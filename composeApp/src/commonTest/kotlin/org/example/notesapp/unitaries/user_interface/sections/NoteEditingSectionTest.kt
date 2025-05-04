package org.example.notesapp.unitaries.user_interface.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.user_interface.sections.NoteEditingSection
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NoteEditingSectionTest {
    @Test
    fun testIfNoteDataIsDisplayedDispatchesTitleAndBodyActionsOnTextChange() = runComposeUiTest {
        var firstValueToBeToggled = false
        var secondValueToBeToggled = false

        setContent {
            NoteEditingSection(
                note = NOTE_OBJECT,
                innerPadding = PaddingValues(0.dp),
                onNoteTitleChange = {
                    firstValueToBeToggled = !firstValueToBeToggled
                },
                onNoteBodyChange = {
                    secondValueToBeToggled = !secondValueToBeToggled
                }
            )
        }

        onNodeWithText(NOTE_TITLE).performTextClearance()
        onNodeWithText(NOTE_BODY).performTextClearance()

        assertTrue(firstValueToBeToggled)
        assertTrue(secondValueToBeToggled)
    }
}