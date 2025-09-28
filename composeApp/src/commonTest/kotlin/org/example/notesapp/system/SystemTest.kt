package org.example.notesapp.system

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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
import org.example.notesapp.constants.data.UserConstants.USER_EXTERNAL_MODEL_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.USERNAME_TEXT_INPUT_LABEL
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.mocks.NoteUpdatingNoteEditingViewModelMock
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.mocks.UserCreatingUserViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.screens.NotesListingScreen
import org.example.notesapp.user_interface.screens.UserSignInScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class SystemTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteEditingViewModel: NoteEditingViewModel

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        userRepository = mock<UserRepository>()
        notesListingViewModel = mock<NotesListingViewModelMock>(autofill)
        noteRepository = mock<NoteRepository>()
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
    }

    @Test
    fun testCreatingUserOnDatabaseAndServiceFromUserInterface() = runComposeUiTest {
        var isUserCreated = false

        every { userViewModel.isUserUsernameInvalidState } returns mutableStateOf(false)
        every { userViewModel.isInternetErrorRisenState } returns mutableStateOf(false)

        userViewModel = UserCreatingUserViewModelMock(userRepository)

        setContent {
            UserSignInScreen(userViewModel = userViewModel, onUserCreated = {
                isUserCreated = true
            })
        }

        onNodeWithText(USERNAME_TEXT_INPUT_LABEL).performTextInput(USER_USERNAME)

        onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

        assertTrue(isUserCreated)
    }

    @Test
    fun testFetchingNotesFromServerAndStoringOnDatabaseFromUserInterface() = runComposeUiTest {
        every { userViewModel.userState } returns mutableStateOf(USER_EXTERNAL_MODEL_OBJECT)
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns mutableStateOf(
            true
        )

        every { notesListingViewModel.isListOfNotesLoadedState } returns mutableStateOf(true)
        every { notesListingViewModel.listOfNotesState } returns mutableStateOf(
            listOf(
                NOTE_OBJECT
            )
        )

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = {})
        }

        onNodeWithText(NOTE_TITLE).assertIsDisplayed()
        onNodeWithText(NOTE_BODY).assertIsDisplayed()

        onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT).assertIsDisplayed()
    }

    @Test
    fun testListingNotesFromDatabaseOnUserInterface() = runComposeUiTest {
        every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns mutableStateOf(
            true
        )
        every { notesListingViewModel.isListOfNotesLoadedState } returns mutableStateOf(true)
        every { notesListingViewModel.listOfNotesState } returns mutableStateOf(listOf(NOTE_OBJECT))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = {})
        }

        onNodeWithText(NOTE_TITLE).assertIsDisplayed()
        onNodeWithText(NOTE_BODY).assertIsDisplayed()

        onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT).assertIsDisplayed()
    }

    @Test
    fun testCreatingNoteOnDatabaseAndServiceFromUserInterface() = runComposeUiTest {
        var isNoteCreated = false

        every{notesListingViewModel.createNote(any(), any())} calls { isNoteCreated = true }

        every { userViewModel.userState } returns mutableStateOf(USER_EXTERNAL_MODEL_OBJECT)
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns mutableStateOf(true)
        every { notesListingViewModel.isListOfNotesLoadedState } returns mutableStateOf(true)
        every { notesListingViewModel.listOfNotesState } returns mutableStateOf(listOf(NOTE_OBJECT))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = { isNoteCreated = true })
        }

        onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT).performClick()

        assertTrue(isNoteCreated)
    }

    @Test
    fun testGettingNoteFromDatabaseOnUserInterface() = runComposeUiTest {
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
    fun testUpdatingNoteOnDatabaseAndServiceFromUserInterface() = runComposeUiTest {
        noteEditingViewModel = NoteUpdatingNoteEditingViewModelMock(noteRepository)

        setContent {
            NoteManipulatingScreen(
                noteId = NOTE_ID,
                noteEditingViewModel = noteEditingViewModel,
                onNoteEditingDone = {})
        }

        onNodeWithText(NOTE_TITLE).performTextClearance()
        onNodeWithText(NOTE_BODY).performTextClearance()
        onNodeWithContentDescription(CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION).performClick()

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

    @Test
    fun testDeletingNoteOnDatabaseAndServiceFromUserInterface() = runComposeUiTest {
        var isNoteDeleted = false

        every {noteEditingViewModel.deleteNote(any())} calls {isNoteDeleted = true}

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