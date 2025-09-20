package org.example.notesapp.unitaries.user_interface.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NoteManipulatingScreenTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
        noteRepository = mock<NoteRepository>()
    }

    @Test
    fun testIfNavigatesWhenTopBarNavigationButton() = runComposeUiTest {
        var isNoteEditingDone = false

        every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)
        every { noteEditingViewModel.noteState } returns mutableStateOf(NOTE_OBJECT)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = { isNoteEditingDone = true })
        }

        onNodeWithContentDescription(BACK_BUTTON_CONTENT_DESCRIPTION).performClick()

        assertTrue(isNoteEditingDone)
    }

    @Test
    fun testIfTopBarConcludeButtonTurnsFalseIsNoteBeingManipulatedState() = runComposeUiTest {
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

        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)

        onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION).performClick()

        assertFalse(noteEditingViewModel.isNoteBeingManipulatedState.value)
    }

    @Test
    fun testIfTopBarEditButtonTurnsTrueIsNoteBeingManipulatedState() = runComposeUiTest {
        every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)
        every { noteEditingViewModel.noteState } returns mutableStateOf(NOTE_OBJECT)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {})
        }

        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(true)

        onNodeWithContentDescription(EDIT_NOTE_CONTENT_DESCRIPTION).performClick()

        assertTrue(noteEditingViewModel.isNoteBeingManipulatedState.value)
    }

    @Test
    fun testIfTopBarDeleteButtonDeletesNoteOnDatabaseAndNavigateToNotesListingScreen() =
        runComposeUiTest {
            var isNoteDeleted = false

            every { noteEditingViewModel.deleteNote(any()) } calls { isNoteDeleted = true}

            every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
            every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(true)
            every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)
            every { noteEditingViewModel.noteState } returns mutableStateOf(NOTE_OBJECT)

            setContent {
                NoteManipulatingScreen(
                    noteId = NOTE_ID,
                    noteEditingViewModel = noteEditingViewModel,
                    onNoteEditingDone = { isNoteDeleted = true })
            }

            onNodeWithContentDescription(DELETE_NOTE_CONTENT_DESCRIPTION).performClick()

            assertTrue(isNoteDeleted)
        }

    @Test
    fun testIfNotesIsLoadedFromDatabaseAndIsDisplayed() = runComposeUiTest {
        every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)
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

    @Test
    fun testIfNotesEditingSectionIsDisplayedWhenNoteBeingManipulatedIsTrue() = runComposeUiTest {
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

    @Test
    fun testIfLoadingSectionIsDisplayedIfNotesIsNotLoaded() = runComposeUiTest {
        every { noteEditingViewModel.isNoteManipulationAbleState } returns mutableStateOf(true)
        every { noteEditingViewModel.isNoteLoadedState } returns mutableStateOf(false)
        every { noteEditingViewModel.isNoteBeingManipulatedState } returns mutableStateOf(false)
        every { noteEditingViewModel.noteState } returns mutableStateOf(NOTE_OBJECT)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {})
        }

        onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION).assertIsDisplayed()
    }

    @Test
    fun testIfWhenNoteDataChangesUpdatesViewModelTitleAndBodyNoteValues() = runComposeUiTest {
        noteEditingViewModel = NoteUpdatingNoteEditingViewModelMock(noteRepository)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {})
        }

        onNodeWithText(NOTE_TITLE).performTextClearance()
        onNodeWithText(NOTE_BODY).performTextClearance()

        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock).noteTitleBeingManipulated
        )
        assertEquals(
            "",
            (noteEditingViewModel as NoteUpdatingNoteEditingViewModelMock).noteBodyBeingManipulated
        )
    }
}
