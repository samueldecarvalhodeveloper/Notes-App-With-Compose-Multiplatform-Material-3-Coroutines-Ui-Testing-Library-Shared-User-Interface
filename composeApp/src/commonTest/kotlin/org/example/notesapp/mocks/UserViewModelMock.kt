package org.example.notesapp.mocks

import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.user_interface.view_models.UserViewModel

open class UserViewModelMock(userRepository: UserRepository) : UserViewModel(userRepository)