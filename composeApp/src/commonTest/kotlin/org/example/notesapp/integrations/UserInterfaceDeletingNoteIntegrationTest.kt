package org.example.notesapp.integrations

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class UserInterfaceDeletingNoteIntegrationTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
        noteRepository = mock<NoteRepository>()
    }

    @Test
    fun testUserInterfaceDeletingNoteOnServerAndOnDatabase() = runComposeUiTest {
        var isNoteDeleted = false

        every { noteEditingViewModel.deleteNote(any()) } calls { isNoteDeleted = true }

        every { noteEditingViewModel.isNoteManipulationAbleState } returns (mutableStateOf(true))
        every { noteEditingViewModel.isNoteLoadedState } returns (mutableStateOf(true))
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns (mutableStateOf(false))
        every { noteEditingViewModel.noteState } returns (mutableStateOf(NOTE_OBJECT))

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = { isNoteDeleted = true })
        }

        onNodeWithContentDescription(DELETE_NOTE_CONTENT_DESCRIPTION).performClick()

        assertTrue(isNoteDeleted)
    }
}