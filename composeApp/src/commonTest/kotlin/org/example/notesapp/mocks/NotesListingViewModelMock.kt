package org.example.notesapp.mocks

import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel

open class NotesListingViewModelMock(noteRepository: NoteRepository) :
    NotesListingViewModel(noteRepository)