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
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_EXTERNAL_MODEL_OBJECT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.screens.NotesListingScreen
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class UserInterfaceCreatingNoteIntegrationTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        notesListingViewModel = mock<NotesListingViewModelMock>(autofill)
        noteRepository = mock<NoteRepository>()
    }

    @Test
    fun testUserInterfaceCreatingNoteOnServerAndOnDatabase() = runComposeUiTest {
        var isNoteCreated = false

        every { notesListingViewModel.createNote(any(), any()) } calls { isNoteCreated = true }

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
}