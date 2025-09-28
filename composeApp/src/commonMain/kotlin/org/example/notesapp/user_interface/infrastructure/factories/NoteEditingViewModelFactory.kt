package org.example.notesapp.user_interface.infrastructure.factories

import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class NoteEditingViewModelFactory {
    fun getViewModelInstance(): NoteEditingViewModel
}