package org.example.notesapp.user_interface.infrastructure.factories

import org.example.notesapp.user_interface.view_models.NotesListingViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class NotesListingViewModelFactory {
    fun getViewModelInstance(): NotesListingViewModel
}