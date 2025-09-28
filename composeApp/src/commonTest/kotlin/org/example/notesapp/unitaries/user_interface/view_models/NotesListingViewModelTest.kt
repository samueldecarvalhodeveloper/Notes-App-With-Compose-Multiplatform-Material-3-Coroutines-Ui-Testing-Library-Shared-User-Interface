package org.example.notesapp.unitaries.user_interface.view_models

import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DATA_FETCHING_DELAY
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NotesListingViewModelTest {
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        noteRepository = mock<NoteRepository>()

        notesListingViewModel = NotesListingViewModel(noteRepository)
    }

    @Test
    fun testIfMethodLoadNotesRetrievesUserNotesFromServiceAndStoresThemOnDatabaseAndAddsThemToListOfNotesState() =
        runBlocking {
            everySuspend { noteRepository.getNotes() } returns listOf()

            notesListingViewModel.loadNotes(USER_ID)

            delay(DATA_FETCHING_DELAY)

            val listOfNotesState =
                notesListingViewModel.listOfNotesState.value
            val isListOfNotesLoaded =
                notesListingViewModel.isListOfNotesLoadedState.value

            assertTrue(listOfNotesState.isEmpty())

            assertTrue(isListOfNotesLoaded)
        }

    @Test
    fun testIfMethodCreateNoteCreatesNoteOnServiceAndDispatchesItsAction() =
        runBlocking {
            var isNoteCreated = false
            var createdNoteId: Int? = null

            everySuspend {
                noteRepository.getCreatedNote("", "", USER_ID)
            } returns NOTE_OBJECT

            notesListingViewModel.createNote(USER_ID) {
                isNoteCreated = true

                createdNoteId = it
            }

            delay(DATA_FETCHING_DELAY)

            assertTrue(isNoteCreated)
            assertEquals(NOTE_ID, createdNoteId)
        }

    @Test
    fun testIfMethodCreateNoteTurnsIsNoteCreationCurrentlyAbleStateToFalseIfNoteCreationFails() =
        runBlocking {
            everySuspend {
                noteRepository.getCreatedNote(
                    NOTE_TITLE,
                    NOTE_BODY,
                    USER_ID
                )
            } throws Exception()

            notesListingViewModel.createNote(USER_ID) {
            }

            delay(DATA_FETCHING_DELAY)

            val isNoteCreationCurrentlyAble =
                notesListingViewModel.isNoteCreationCurrentlyAbleState.value

            assertFalse(isNoteCreationCurrentlyAble)
        }
}