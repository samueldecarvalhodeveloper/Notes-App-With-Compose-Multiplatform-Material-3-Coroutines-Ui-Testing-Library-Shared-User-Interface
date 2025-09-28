package org.example.notesapp.user_interface.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository

open class NoteEditingViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _noteState = mutableStateOf<Note?>(null)
    open val noteState: State<Note?> = _noteState
    private val _isNoteManipulationAbleState = mutableStateOf(true)
    open val isNoteManipulationAbleState: State<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState = mutableStateOf(false)
    open val isNoteLoadedState: State<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState = mutableStateOf(false)
    open val isNoteBeingManipulatedState: State<Boolean> = _isNoteBeingManipulatedState

    open fun loadNote(noteId: Int) {
        viewModelScope.launch {
            val noteFromDatabase = noteRepository.getNote(noteId)

            _noteState.value = noteFromDatabase

            _isNoteLoadedState.value = true
        }
    }

    open fun manipulateNote() {
        _isNoteBeingManipulatedState.value = _isNoteManipulationAbleState.value!!
    }

    open fun setNoteTitle(title: String) {
        _noteState.value!!.title = title
    }

    open fun setNoteBody(body: String) {
        _noteState.value!!.body = body
    }

    open fun concludeNote() {
        viewModelScope.launch {
            try {
                val updatedNoteOnService = noteRepository.getUpdatedNote(
                    _noteState.value!!.id,
                    _noteState.value!!.title,
                    _noteState.value!!.body,
                    _noteState.value!!.userId
                )

                _isNoteBeingManipulatedState.value = false

                _noteState.value = updatedNoteOnService
            } catch (_: Exception) {
                _isNoteManipulationAbleState.value = false

                _isNoteBeingManipulatedState.value = false
            }
        }
    }

    open fun deleteNote(onNoteDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(_noteState.value!!.id, _noteState.value!!.userId)

                onNoteDeleted()
            } catch (_: Exception) {
                _isNoteManipulationAbleState.value = false

                _isNoteBeingManipulatedState.value = false
            }
        }
    }
}