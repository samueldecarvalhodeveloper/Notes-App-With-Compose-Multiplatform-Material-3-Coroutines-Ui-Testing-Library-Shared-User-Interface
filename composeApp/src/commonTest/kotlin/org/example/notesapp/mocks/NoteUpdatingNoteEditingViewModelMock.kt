package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

class NoteUpdatingNoteEditingViewModelMock(noteRepository: NoteRepository)  :
    NoteEditingViewModel(noteRepository) {
    private val _noteState = mutableStateOf<Note?>(NOTE_OBJECT)
    override val noteState: State<Note?> = _noteState
    private val _isNoteManipulationAbleState = mutableStateOf(true)
    override val isNoteManipulationAbleState: State<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState = mutableStateOf(true)
    override val isNoteLoadedState: State<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState = mutableStateOf(true)
    override val isNoteBeingManipulatedState: State<Boolean> = _isNoteBeingManipulatedState
    lateinit var noteTitleBeingManipulated: String
    lateinit var noteBodyBeingManipulated: String

    override fun loadNote(noteId: Int) {
    }

    override fun manipulateNote() {
    }

    override fun setNoteTitle(title: String) {
        noteTitleBeingManipulated = title
    }

    override fun setNoteBody(body: String) {
        noteBodyBeingManipulated = body
    }

    override fun concludeNote() {
        _noteState.value = Note(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)
    }

    override fun deleteNote(onNoteDeleted: () -> Unit) {
    }
}