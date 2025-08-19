package org.example.notesapp.integrations

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class UserInterfaceGettingNoteIntegrationTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel

    @BeforeTest
    fun beforeEach() {
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
    }

    @Test
    fun testGettingNoteFromDatabaseOnUserInterface() = runComposeUiTest {
        every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(true)
        every { noteEditingViewModel.noteState } returns mutableStateOf(NOTE_OBJECT)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {})
        }

        onNodeWithText(NOTE_TITLE).assertIsDisplayed()

        onNodeWithText(NOTE_BODY).assertIsDisplayed()
    }
}