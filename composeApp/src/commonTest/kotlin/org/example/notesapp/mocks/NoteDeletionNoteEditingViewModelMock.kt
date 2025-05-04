package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

open class NoteDeletionNoteEditingViewModelMock(noteRepository: NoteRepository) :
    NoteEditingViewModel(noteRepository) {
    private val _noteState = mutableStateOf<Note?>(null)
    override val noteState: State<Note?> = _noteState
    private val _isNoteManipulationAbleState = mutableStateOf(true)
    override val isNoteManipulationAbleState: State<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState = mutableStateOf(false)
    override val isNoteLoadedState: State<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState = mutableStateOf(false)
    override val isNoteBeingManipulatedState: State<Boolean> = _isNoteBeingManipulatedState
    private lateinit var noteTitleBeingManipulated: String
    private lateinit var noteBodyBeingManipulated: String

    override fun loadNote(noteId: Int) {
    }

    override fun manipulateNote() {
    }

    override fun setNoteTitle(title: String) {
    }

    override fun setNoteBody(body: String) {
    }

    override fun concludeNote() {
    }

    override fun deleteNote(onNoteDeleted: () -> Unit) {
        onNoteDeleted()
    }
}