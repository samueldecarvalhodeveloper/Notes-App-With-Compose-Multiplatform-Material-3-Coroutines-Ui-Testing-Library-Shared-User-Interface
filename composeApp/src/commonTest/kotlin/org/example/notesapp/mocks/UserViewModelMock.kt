package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.test.runTest
import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.user_interface.view_models.UserViewModel

open class UserViewModelMock(private val userRepository: UserRepository) :
    UserViewModel(userRepository) {
    private val _userState = mutableStateOf<User?>(null)
    open override val userState: State<User?> = _userState
    private val _isInternetErrorRisenState = mutableStateOf(false)
    open override val isInternetErrorRisenState: State<Boolean> = _isInternetErrorRisenState
    private val _isUserUsernameInvalidState = mutableStateOf(false)
    open override val isUserUsernameInvalidState: State<Boolean> = _isUserUsernameInvalidState

    open override fun verifyIfUserExists(onUserCreated: () -> Unit) {
        runTest {
            try {
                val userFromDatabase = userRepository.getUser()

                _userState.value = userFromDatabase

                onUserCreated()
            } catch (_: Exception) {
            }
        }
    }

    open override fun createUser(username: String, onUserCreated: () -> Unit) {
        if (username.isEmpty()) {
            _isUserUsernameInvalidState.value = true
        } else {
            _isUserUsernameInvalidState.value = false

            runTest {
                try {
                    val createdUserOnService = userRepository.getCreatedUser(username)

                    _userState.value = createdUserOnService

                    onUserCreated()
                } catch (_: Exception) {
                    _isInternetErrorRisenState.value = true
                }
            }
        }
    }
}