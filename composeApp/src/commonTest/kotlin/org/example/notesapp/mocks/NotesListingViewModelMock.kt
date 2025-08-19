package org.example.notesapp.mocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.test.runTest
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel

open class NotesListingViewModelMock(private val noteRepository: NoteRepository) :
    NotesListingViewModel(noteRepository) {
    private val _listOfNotesState = mutableStateOf<List<Note>>(listOf())
    open override val listOfNotesState: State<List<Note>> = _listOfNotesState
    private val _isListOfNotesLoadedState = mutableStateOf(false)
    open override val isListOfNotesLoadedState: State<Boolean> = _isListOfNotesLoadedState
    private val _isNoteCreationCurrentlyAbleState = mutableStateOf(true)
    open override val isNoteCreationCurrentlyAbleState: State<Boolean> =
        _isNoteCreationCurrentlyAbleState

    open override fun loadNotes(userId: Int) {
        runTest {
            try {
                noteRepository.fetchNotesFromService(userId)
            } catch (_: Exception) {
            } finally {
                val listOfNotesFromDatabase = noteRepository.getNotes()

                _listOfNotesState.value = listOfNotesFromDatabase

                _isListOfNotesLoadedState.value = true
            }
        }
    }

    open override fun createNote(userId: Int, onNoteCreated: (Int) -> Unit) {
        runTest {
            try {
                val createdNote = noteRepository.getCreatedNote("", "", userId)

                onNoteCreated(createdNote.id)
            } catch (_: Exception) {
                _isNoteCreationCurrentlyAbleState.value = false
            }
        }
    }
}