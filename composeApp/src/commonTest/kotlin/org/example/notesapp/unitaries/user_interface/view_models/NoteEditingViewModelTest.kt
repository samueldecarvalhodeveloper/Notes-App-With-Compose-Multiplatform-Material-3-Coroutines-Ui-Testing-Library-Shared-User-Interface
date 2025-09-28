package org.example.notesapp.unitaries.user_interface.view_models

import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT_WITH_WRONG_DATA_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DATA_FETCHING_DELAY
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NoteEditingViewModelTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        noteRepository = mock<NoteRepository>()

        noteEditingViewModel = NoteEditingViewModel(noteRepository)
    }

    @Test
    fun testIfMethodLoadNoteLoadsNoteFromDatabaseAndSetsNoteStateAndCurrentNoteBeingEditedAndTurnsIsNoteLoadedStateToTrue() =
        runBlocking {
            everySuspend { noteRepository.getNote(NOTE_ID) } returns NOTE_OBJECT

            noteEditingViewModel.loadNote(NOTE_ID)

            delay(DATA_FETCHING_DELAY)

            val noteFromDatabase = noteEditingViewModel.noteState.value!!

            val isNoteLoaded = noteEditingViewModel.isNoteLoadedState.value

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)

            assertTrue(isNoteLoaded)
        }

    @Test
    fun testIfMethodManipulateNoteTurnsIsNoteBeingManipulatedStateToTrueIfIsNoteManipulationAbleStateIsTrue() {
        noteEditingViewModel.manipulateNote()

        val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value

        assertTrue(isNoteBeingManipulated)
    }

    @Test
    fun testIfMethodConcludeNoteUpdatesNoteOnServiceAndOnDatabaseAndTurnsIsNoteBeingManipulatedStateToFalseAndUpdatesNoteState() =
        runBlocking {
            everySuspend { noteRepository.getNote(NOTE_ID) } returns NOTE_OBJECT

            noteEditingViewModel.loadNote(NOTE_ID)

            delay(DATA_FETCHING_DELAY)

            noteEditingViewModel.setNoteTitle(NOTE_TITLE)
            noteEditingViewModel.setNoteBody(NOTE_BODY)

            noteEditingViewModel.concludeNote()

            delay(DATA_FETCHING_DELAY)

            val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value
            val noteState = noteEditingViewModel.noteState.value!!

            assertFalse(isNoteBeingManipulated)

            assertEquals(NOTE_ID, noteState.id)
            assertEquals(NOTE_TITLE, noteState.title)
            assertEquals(NOTE_BODY, noteState.body)
            assertEquals(NOTE_CREATED_AT, noteState.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteState.updatedAt)
            assertEquals(USER_ID, noteState.userId)
        }

    @Test
    fun testIfMethodConcludeNoteOnNoteUpdatingFailTurnsIsNoteManipulationAbleStateToFalseIsNoteBeingManipulatedStateToFalse() =
        runBlocking {
            everySuspend {
                noteRepository.getNote(NOTE_ID)
            } returns NOTE_OBJECT_WITH_WRONG_DATA_OBJECT
            everySuspend {
                noteRepository.getCreatedNote("", "", USER_ID)
            } throws Exception()

            noteEditingViewModel.loadNote(NOTE_ID)

            noteEditingViewModel.concludeNote()

            delay(DATA_FETCHING_DELAY)

            val isNoteManipulationAble = noteEditingViewModel.isNoteManipulationAbleState.value
            val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value

            assertFalse(isNoteManipulationAble)
            assertFalse(isNoteBeingManipulated)
        }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnServiceAndOnDatabaseAndDispatchesItsAction() =
        runBlocking {
            var isNoteDeleted = false
            var isNoteDeletedOnDataSource = false

            everySuspend {
                noteRepository.getNote(NOTE_ID)
            } returns NOTE_OBJECT
            everySuspend { noteRepository.deleteNote(NOTE_ID, USER_ID) } calls {
                isNoteDeletedOnDataSource = true
            }

            noteEditingViewModel.loadNote(NOTE_ID)

            noteEditingViewModel.deleteNote {
                isNoteDeleted = true
            }

            delay(DATA_FETCHING_DELAY)

            assertTrue(isNoteDeleted)
            assertTrue(isNoteDeletedOnDataSource)
        }

    @Test
    fun testIfMethodDeleteNoteTurnsIsNoteBeingManipulatedStateToFalseAndIsNoteManipulationAbleStateToFalse() =
        runBlocking {
            everySuspend {
                noteRepository.deleteNote(NOTE_ID, USER_ID)
            } throws Exception()

            noteEditingViewModel.deleteNote {}

            delay(DATA_FETCHING_DELAY)

            val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value!!
            val isNoteManipulationAble = noteEditingViewModel.isNoteManipulationAbleState.value!!

            assertFalse(isNoteBeingManipulated)
            assertFalse(isNoteManipulationAble)
        }
}
