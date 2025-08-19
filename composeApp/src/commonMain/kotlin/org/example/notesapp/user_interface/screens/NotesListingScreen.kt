package org.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.notesapp.user_interface.components.CreateNoteFloatingActionButton
import org.example.notesapp.user_interface.components.GreetingAppTopBar
import org.example.notesapp.user_interface.sections.ListOfNotesSection
import org.example.notesapp.user_interface.sections.LoadingSection
import org.example.notesapp.user_interface.sections.NoNotesSection
import org.example.notesapp.user_interface.theme.NEUTRALS_100
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel

@Composable
fun NotesListingScreen(
    notesListingViewModel: NotesListingViewModel,
    userViewModel: UserViewModel,
    onNoteSelected: (Int)-> Unit
) {
    val user by userViewModel.userState
    val isNoteCreationCurrentlyAble by notesListingViewModel.isNoteCreationCurrentlyAbleState
    val isListOfNotesLoaded by notesListingViewModel.isListOfNotesLoadedState
    val listOfNotes by notesListingViewModel.listOfNotesState

    LaunchedEffect(Unit) {
        notesListingViewModel.loadNotes(user!!.id)
    }

    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .background(NEUTRALS_100)
                .fillMaxSize(),
            topBar = { GreetingAppTopBar(user!!.username) },
            floatingActionButton = {
                if (isNoteCreationCurrentlyAble && isListOfNotesLoaded) {
                    CreateNoteFloatingActionButton {
                        notesListingViewModel.createNote(userId = user!!.id) {
                            onNoteSelected(it)
                        }
                    }
                }
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isListOfNotesLoaded) {
                    if (listOfNotes.isEmpty()) {
                        NoNotesSection()
                    } else {
                        ListOfNotesSection(listOfNotes = listOfNotes) {
                            onNoteSelected(it)
                        }
                    }
                } else {
                    LoadingSection()
                }
            }
        }
    }
}