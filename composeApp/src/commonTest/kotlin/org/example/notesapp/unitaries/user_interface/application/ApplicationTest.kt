package org.example.notesapp.unitaries.user_interface.application

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.application.Application
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ApplicationTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteEditingViewModel: NoteEditingViewModel

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        notesListingViewModel = mock<NotesListingViewModelMock>(autofill)
        noteEditingViewModel = mock<NoteEditingViewModelMock>(autofill)
    }

    @Test
    fun testIfRoutesAreSet() = runComposeUiTest {
        every { userViewModel.isUserUsernameInvalidState } returns mutableStateOf(false)
        every { userViewModel.isInternetErrorRisenState } returns mutableStateOf(false)

        setContent {
            Application(
                userViewModel,
                { notesListingViewModel },
                { noteEditingViewModel }
            )
        }

        onNodeWithText(CREATE_USER_BUTTON_TEXT).assertExists()
    }
}