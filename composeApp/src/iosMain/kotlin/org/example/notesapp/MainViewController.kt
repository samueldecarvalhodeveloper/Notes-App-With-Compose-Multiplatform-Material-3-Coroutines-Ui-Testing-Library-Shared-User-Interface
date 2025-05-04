package org.example.notesapp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.notesapp.user_interface.application.Application
import org.example.notesapp.user_interface.infrastructure.factories.NoteEditingViewModelFactory
import org.example.notesapp.user_interface.infrastructure.factories.NotesListingViewModelFactory
import org.example.notesapp.user_interface.infrastructure.factories.UserViewModelFactory

fun MainViewController() = ComposeUIViewController {
    val userViewModelFactory = remember { UserViewModelFactory() }
    val notesListingViewModelFactory = remember { NotesListingViewModelFactory() }
    val noteEditingViewModelFactory = remember { NoteEditingViewModelFactory() }
    val userViewModel = remember { userViewModelFactory.getViewModelInstance() }

    Application(
        userViewModel = userViewModel,
        notesListingViewModelFactory = { notesListingViewModelFactory.getViewModelInstance() },
        noteEditingViewModelFactory = { noteEditingViewModelFactory.getViewModelInstance() })
}