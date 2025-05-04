package org.example.notesapp.unitaries.user_interface.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.user_interface.sections.NoteVisualizingSection
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class NoteVisualizingSectionTest {
    @Test
    fun testIfNoteDataIsDisplayed() = runComposeUiTest {
        setContent {
            NoteVisualizingSection(note = NOTE_OBJECT, innerPadding = PaddingValues(0.dp))
        }

        onNodeWithText(NOTE_TITLE).assertIsDisplayed()
        onNodeWithText(NOTE_BODY).assertIsDisplayed()

    }
}