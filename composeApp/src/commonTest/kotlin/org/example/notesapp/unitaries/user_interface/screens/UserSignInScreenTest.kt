package org.example.notesapp.unitaries.user_interface.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_AVAILABLE_INTERNET_ERROR_MESSAGE
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_VALID_USERNAME_ERROR_MESSAGE
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.USERNAME_TEXT_INPUT_LABEL
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.mocks.UserCreatingUserViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.screens.UserSignInScreen
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class UserSignInScreenTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        userRepository = mock<UserRepository>()
    }

    @Test
    fun testIfNotValidUsernameErrorMessageIsDisplayedWhenNotValidUserUsernameIsSubmitted() =
        runComposeUiTest {
            every { userViewModel.isUserUsernameInvalidState } returns mutableStateOf(true)
            every { userViewModel.isInternetErrorRisenState } returns mutableStateOf(false)

            setContent {
                UserSignInScreen(userViewModel = userViewModel, onUserCreated = {})
            }

            onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

            onNodeWithText(NOT_VALID_USERNAME_ERROR_MESSAGE).assertExists()
        }

    @Test
    fun testIfNotAvailableInternetErrorMessageIsDisplayedWhenInternetIsNotAvailableOnUserCreationSubmission() =
        runComposeUiTest {
            every { userViewModel.isUserUsernameInvalidState } returns mutableStateOf(false)
            every { userViewModel.isInternetErrorRisenState } returns mutableStateOf(true)

            setContent {
                UserSignInScreen(userViewModel = userViewModel, onUserCreated = {})
            }

            onNodeWithText(CREATE_USER_BUTTON_TEXT).performClick()

            onNodeWithText(NOT_AVAILABLE_INTERNET_ERROR_MESSAGE).assertExists()
        }

    @Test
    fun testIfUserInterfaceNavigatesToUserListOfNotesIfUserIsCreated() = runComposeUiTest {
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
}
