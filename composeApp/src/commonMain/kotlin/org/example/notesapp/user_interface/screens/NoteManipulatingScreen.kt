package org.example.notesapp.user_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.example.notesapp.user_interface.components.NoteManipulationAppTopBar
import org.example.notesapp.user_interface.sections.LoadingSection
import org.example.notesapp.user_interface.sections.NoteEditingSection
import org.example.notesapp.user_interface.sections.NoteVisualizingSection
import org.example.notesapp.user_interface.theme.NEUTRALS_100
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

@Composable
fun NoteManipulatingScreen(
    noteId: Int,
    noteEditingViewModel: NoteEditingViewModel,
    onNoteEditingDone: () -> Unit
) {
    val isNoteManipulationAble by noteEditingViewModel.isNoteManipulationAbleState
    val isNoteLoaded by noteEditingViewModel.isNoteLoadedState
    val isNoteBeingManipulated by noteEditingViewModel.isNoteBeingManipulatedState
    val note by noteEditingViewModel.noteState

    LaunchedEffect(Unit) {
        noteEditingViewModel.loadNote(noteId)
    }

    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .background(NEUTRALS_100)
                .fillMaxSize(),
            topBar = {
                NoteManipulationAppTopBar(
                    isManipulateNoteButtonAble = isNoteManipulationAble && isNoteLoaded && isNoteBeingManipulated.not(),
                    isConcludeNoteButtonAble = isNoteManipulationAble && isNoteLoaded && isNoteBeingManipulated,
                    isDeleteNoteButtonAble = isNoteManipulationAble && isNoteLoaded,
                    onNavigationIconButtonClick = {
                        onNoteEditingDone()
                    },
                    onConcludeNoteIconButtonClick = { noteEditingViewModel.concludeNote() },
                    onEditNoteIconButtonClick = { noteEditingViewModel.manipulateNote() },
                    onDeleteNoteIconButtonClick = {
                        noteEditingViewModel.deleteNote {
                            onNoteEditingDone()
                        }
                    },
                )
            }
        ) { innerPadding ->
            if (isNoteLoaded) {
                if (isNoteBeingManipulated) {
                    NoteEditingSection(
                        note = note!!,
                        innerPadding = innerPadding,
                        onNoteTitleChange = {
                            noteEditingViewModel.setNoteTitle(it)
                        },
                        onNoteBodyChange = {
                            noteEditingViewModel.setNoteBody(it)
                        }
                    )
                } else {
                    NoteVisualizingSection(note!!, innerPadding)
                }
            } else {
                LoadingSection()
            }
        }
    }
}
