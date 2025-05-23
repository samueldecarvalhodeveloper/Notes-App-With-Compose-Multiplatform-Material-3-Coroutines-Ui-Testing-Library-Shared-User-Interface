package org.example.notesapp.user_interface.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.repositories.UserRepository

open class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userState = mutableStateOf<User?>(null)
    open val userState: State<User?> = _userState
    private val _isInternetErrorRisenState = mutableStateOf(false)
    open val isInternetErrorRisenState: State<Boolean> = _isInternetErrorRisenState
    private val _isUserUsernameInvalidState = mutableStateOf(false)
    open val isUserUsernameInvalidState: State<Boolean> = _isUserUsernameInvalidState

    open fun verifyIfUserExists(onUserCreated: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userFromDatabase = userRepository.getUser()

                withContext(Main) {
                    _userState.value = userFromDatabase

                    onUserCreated()
                }
            } catch (_: Exception) {
            }
        }
    }

    open fun createUser(username: String, onUserCreated: () -> Unit) {
        if (username.isEmpty()) {
            _isUserUsernameInvalidState.value = true
        } else {
            _isUserUsernameInvalidState.value = false

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val createdUserOnService = userRepository.getCreatedUser(username)

                    withContext(Main) {
                        _userState.value = createdUserOnService

                        onUserCreated()
                    }
                } catch (_: Exception) {
                    withContext(Main) {
                        _isInternetErrorRisenState.value = true
                    }
                }
            }
        }
    }
}
