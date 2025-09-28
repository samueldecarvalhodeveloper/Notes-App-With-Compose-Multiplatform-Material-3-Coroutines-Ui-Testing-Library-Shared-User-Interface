package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.user_interface.view_models.UserViewModel

class UserCreatingUserViewModelMock(userRepository: UserRepository) : UserViewModel(userRepository) {
    private val _userState = mutableStateOf<User?>(null)
    override val userState: State<User?> = _userState
    private val _isInternetErrorRisenState = mutableStateOf(false)
    override val isInternetErrorRisenState: State<Boolean> = _isInternetErrorRisenState
    private val _isUserUsernameInvalidState = mutableStateOf(false)
    override val isUserUsernameInvalidState: State<Boolean> = _isUserUsernameInvalidState

    override fun createUser(username: String, onUserCreated: () -> Unit) {
        if (username.isEmpty()) {
            _isUserUsernameInvalidState.value = true
        } else {
            onUserCreated()
        }
    }
}
