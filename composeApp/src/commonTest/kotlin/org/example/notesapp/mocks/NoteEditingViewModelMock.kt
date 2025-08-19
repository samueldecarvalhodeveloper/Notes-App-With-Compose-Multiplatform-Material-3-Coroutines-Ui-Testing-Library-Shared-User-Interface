package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

open class NoteEditingViewModelMock(private val noteRepository: NoteRepository) :
    NoteEditingViewModel(noteRepository) {
    private val _noteState = mutableStateOf<Note?>(null)
    open override val noteState: State<Note?> = _noteState
    private val _isNoteManipulationAbleState = mutableStateOf(true)
    open override val isNoteManipulationAbleState: State<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState = mutableStateOf(false)
    open override val isNoteLoadedState: State<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState = mutableStateOf(false)
    open override val isNoteBeingManipulatedState: State<Boolean> = _isNoteBeingManipulatedState
    open var noteTitleBeingManipulated: String = ""
    open var noteBodyBeingManipulated: String = ""

    open override fun loadNote(noteId: Int) {
        runTest {
            val noteFromDatabase = noteRepository.getNote(noteId)

            noteTitleBeingManipulated = noteFromDatabase.title
            noteBodyBeingManipulated = noteFromDatabase.body

            _noteState.value = noteFromDatabase

            _isNoteLoadedState.value = true
        }
    }

    open override fun manipulateNote() {
        _isNoteBeingManipulatedState.value = _isNoteManipulationAbleState.value!!
    }

    open override fun setNoteTitle(title: String) {
        noteTitleBeingManipulated = title
    }

    open override fun setNoteBody(body: String) {
        noteBodyBeingManipulated = body
    }

    open override fun concludeNote() {
        runTest {
            try {
                val updatedNoteOnService = noteRepository.getUpdatedNote(
                    _noteState.value!!.id,
                    noteTitleBeingManipulated,
                    noteBodyBeingManipulated,
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

    open override fun deleteNote(onNoteDeleted: () -> Unit) {
        runTest {
            try {
                noteRepository.deleteNote(_noteState.value!!.id, _noteState.value!!.userId)

                withContext(Main) {
                    onNoteDeleted()
                }
            } catch (_: Exception) {
                _isNoteManipulationAbleState.value = false

                _isNoteBeingManipulatedState.value = false
            }
        }
    }
}