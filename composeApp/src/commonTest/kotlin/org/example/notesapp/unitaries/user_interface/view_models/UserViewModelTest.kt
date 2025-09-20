package org.example.notesapp.unitaries.user_interface.view_models

import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.example.notesapp.constants.data.UserConstants.USER_EXTERNAL_MODEL_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DATA_FETCHING_DELAY
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserViewModelTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    @BeforeTest
    fun beforeEach() {
        userRepository = mock<UserRepository>()

        userViewModel = UserViewModel(userRepository)
    }

    @Test
    fun testIfVerifyIfUserExistsSetsUserStateAndDispatchesItsAction() =
        runBlocking {
            var isUserCreated = false

            everySuspend { userRepository.getUser() } returns USER_EXTERNAL_MODEL_OBJECT

            userViewModel.verifyIfUserExists {
                isUserCreated = true
            }

            delay(DATA_FETCHING_DELAY)

            assertTrue(isUserCreated)
    }

    @Test
    fun testIfMethodCreateUserTurnsIsUserUsernameInvalidTrueIfUsernameIsEmpty() =
        runBlocking {
            userViewModel.createUser("") {}

            delay(DATA_FETCHING_DELAY)

            val isUserUsernameInvalid = userViewModel.isUserUsernameInvalidState.value

            assertTrue(isUserUsernameInvalid)
        }

    @Test
    fun testIfMethodCreateUserTurnsIsInternetErrorRisenTrueIfServiceAccessIsNotAvailable() =
        runBlocking {
            everySuspend { userRepository.getCreatedUser(USER_USERNAME) } throws Exception()

            userViewModel.createUser(USER_USERNAME) {}

            delay(DATA_FETCHING_DELAY)

            val isInternetErrorRisen = userViewModel.isInternetErrorRisenState.value

            assertTrue(isInternetErrorRisen)
        }

    @Test
    fun testIfMethodCreateUserCreatesUserAndAddsItToUserStateAndDispatchesItsAction() =
        runBlocking {
            var isUserCreated = false

            everySuspend { userRepository.getCreatedUser(USER_USERNAME) } returns USER_EXTERNAL_MODEL_OBJECT

            userViewModel.createUser(USER_USERNAME) {
                isUserCreated = true
            }

            delay(DATA_FETCHING_DELAY)

            val createdUser = userViewModel.userState.value!!

            assertEquals(USER_ID, createdUser.id)
            assertEquals(USER_USERNAME, createdUser.username)

            assertTrue(isUserCreated)
        }
}
