package org.example.notesapp.mocks

import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

open class NoteEditingViewModelMock(noteRepository: NoteRepository) :
    NoteEditingViewModel(noteRepository)