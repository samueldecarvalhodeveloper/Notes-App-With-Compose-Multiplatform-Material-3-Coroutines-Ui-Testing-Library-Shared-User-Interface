package org.example.notesapp.integrations

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.UserConstants.USER_EXTERNAL_MODEL_OBJECT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.screens.NotesListingScreen
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class UserInterfaceFetchingNotesFromServiceIntegrationTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var notesListingViewModel: NotesListingViewModel

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        notesListingViewModel = mock<NotesListingViewModelMock>(autofill)
    }

    @Test
    fun testFetchingNotesFromServerAndStoringOnDatabaseFromUserInterface() = runComposeUiTest {
        every { userViewModel.userState } returns mutableStateOf(USER_EXTERNAL_MODEL_OBJECT)
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns mutableStateOf(true)
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
}