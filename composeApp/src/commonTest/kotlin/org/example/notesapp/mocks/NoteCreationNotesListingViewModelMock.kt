package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel

open class NoteCreationNotesListingViewModelMock(noteRepository: NoteRepository) :
    NotesListingViewModel(noteRepository) {
    private val _listOfNotesState = mutableStateOf<List<Note>>(listOf())
    open override val listOfNotesState: State<List<Note>> = _listOfNotesState
    private val _isListOfNotesLoadedState = mutableStateOf(false)
    open override val isListOfNotesLoadedState: State<Boolean> = _isListOfNotesLoadedState
    private val _isNoteCreationCurrentlyAbleState = mutableStateOf(true)
    open override val isNoteCreationCurrentlyAbleState: State<Boolean> =
        _isNoteCreationCurrentlyAbleState

    open override fun loadNotes(userId: Int) {
    }

    open override fun createNote(userId: Int, onNoteCreated: (Int) -> Unit) {
        onNoteCreated(NOTE_ID)
    }
}