package org.example.notesapp.user_interface.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository

open class NotesListingViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _listOfNotesState = mutableStateOf<List<Note>>(listOf())
    open val listOfNotesState: State<List<Note>> = _listOfNotesState
    private val _isListOfNotesLoadedState = mutableStateOf(false)
    open val isListOfNotesLoadedState: State<Boolean> = _isListOfNotesLoadedState
    private val _isNoteCreationCurrentlyAbleState = mutableStateOf(true)
    open val isNoteCreationCurrentlyAbleState: State<Boolean> = _isNoteCreationCurrentlyAbleState

    open fun loadNotes(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.fetchNotesFromService(userId)
            } catch (_: Exception) {
            } finally {
                val listOfNotesFromDatabase = noteRepository.getNotes()

                withContext(Main) {
                    _listOfNotesState.value = listOfNotesFromDatabase

                    _isListOfNotesLoadedState.value = true
                }
            }
        }
    }

    open fun createNote(userId: Int, onNoteCreated: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdNote = noteRepository.getCreatedNote("", "", userId)

                withContext(Main) {
                    onNoteCreated(createdNote.id)
                }
            } catch (_: Exception) {
                withContext(Main) {
                    _isNoteCreationCurrentlyAbleState.value = false
                }
            }
        }
    }
}
