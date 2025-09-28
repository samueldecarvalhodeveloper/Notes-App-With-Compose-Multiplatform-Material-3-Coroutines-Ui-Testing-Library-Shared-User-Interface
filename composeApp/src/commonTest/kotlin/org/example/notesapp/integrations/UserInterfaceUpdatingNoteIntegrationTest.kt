package org.example.notesapp.integrations

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class UserInterfaceUpdatingNoteIntegrationTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteEditingViewModel: NoteEditingViewModel

    @BeforeTest
    fun beforeEach() {
        noteRepository = mock<NoteRepository>()
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
    }

    @Test
    fun testUserInterfaceUpdatingNoteOnServerAndOnDatabase() = runComposeUiTest {
        noteEditingViewModel = NoteUpdatingNoteEditingViewModelMock(noteRepository)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {}
            )
        }

        onNodeWithText(NOTE_TITLE).performTextClearance()
        onNodeWithText(NOTE_BODY).performTextClearance()
        onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION)
            .performClick()

        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock).noteTitleBeingManipulated
        )
        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock).noteBodyBeingManipulated
        )

        assertEquals("", noteEditingViewModel.noteState.value!!.title)
        assertEquals("", noteEditingViewModel.noteState.value!!.body)
    }
}