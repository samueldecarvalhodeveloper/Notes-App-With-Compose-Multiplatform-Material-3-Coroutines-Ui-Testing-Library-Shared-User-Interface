package org.example.notesapp.user_interface.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private lateinit var noteTitleBeingManipulated: String
    private lateinit var noteBodyBeingManipulated: String

    open fun loadNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteFromDatabase = noteRepository.getNote(noteId)

            noteTitleBeingManipulated = noteFromDatabase.title
            noteBodyBeingManipulated = noteFromDatabase.body

            withContext(Main) {
                _noteState.value = noteFromDatabase

                _isNoteLoadedState.value = true
            }
        }
    }

    open fun manipulateNote() {
        _isNoteBeingManipulatedState.value = _isNoteManipulationAbleState.value!!
    }

    open fun setNoteTitle(title: String) {
        noteTitleBeingManipulated = title
    }

    open fun setNoteBody(body: String) {
        noteBodyBeingManipulated = body
    }

    open fun concludeNote() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedNoteOnService = noteRepository.getUpdatedNote(
                    _noteState.value!!.id,
                    noteTitleBeingManipulated,
                    noteBodyBeingManipulated,
                    _noteState.value!!.userId
                )

                withContext(Main) {
                    _isNoteBeingManipulatedState.value = false

                    _noteState.value = updatedNoteOnService
                }
            } catch (_: Exception) {
                withContext(Main) {
                    _isNoteManipulationAbleState.value = false

                    _isNoteBeingManipulatedState.value = false
                }
            }
        }
    }

    open fun deleteNote(onNoteDeleted: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.deleteNote(_noteState.value!!.id, _noteState.value!!.userId)

                withContext(Main) {
                    onNoteDeleted()
                }
            } catch (_: Exception) {
                withContext(Main) {
                    _isNoteManipulationAbleState.value = false

                    _isNoteBeingManipulatedState.value = false
                }
            }
        }
    }
}