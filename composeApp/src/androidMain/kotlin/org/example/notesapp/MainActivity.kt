package org.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import org.example.notesapp.user_interface.application.Application
import org.example.notesapp.user_interface.infrastructure.factories.NoteEditingViewModelFactory
import org.example.notesapp.user_interface.infrastructure.factories.NotesListingViewModelFactory
import org.example.notesapp.user_interface.infrastructure.factories.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userViewModelFactory = remember { UserViewModelFactory(applicationContext) }
            val notesListingViewModelFactory = remember { NotesListingViewModelFactory(applicationContext) }
            val noteEditingViewModelFactory = remember { NoteEditingViewModelFactory(applicationContext) }
            val userViewModel = remember { userViewModelFactory.getViewModelInstance() }

            Application(
                userViewModel = userViewModel,
                notesListingViewModelFactory = { notesListingViewModelFactory.getViewModelInstance() },
                noteEditingViewModelFactory = { noteEditingViewModelFactory.getViewModelInstance() })
        }
    }
}
