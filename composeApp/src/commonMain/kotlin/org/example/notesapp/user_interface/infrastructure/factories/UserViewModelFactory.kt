package org.example.notesapp.user_interface.infrastructure.factories

import org.example.notesapp.user_interface.view_models.UserViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class UserViewModelFactory {
    fun getViewModelInstance(): UserViewModel
}