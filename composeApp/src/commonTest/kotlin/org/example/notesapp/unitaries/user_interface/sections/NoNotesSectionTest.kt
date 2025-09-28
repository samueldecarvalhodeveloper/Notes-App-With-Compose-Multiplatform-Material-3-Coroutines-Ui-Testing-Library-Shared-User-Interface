package org.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NO_NOTES_LABEL_TEXT
import org.example.notesapp.user_interface.sections.NoNotesSection
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class NoNotesSectionTest {
    @Test
    fun testIfNoNotesLabelIsDisplayed() = runComposeUiTest {
        setContent {
            NoNotesSection()
        }

        onNodeWithText(NO_NOTES_LABEL_TEXT).assertIsDisplayed()
    }
}