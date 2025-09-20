package org.example.notesapp.user_interface.infrastructure.factories

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.example.notesapp.data.local_data_source.databases.NoteDatabase
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class NotesListingViewModelFactory {
    actual fun getViewModelInstance(): NotesListingViewModel {
        val httpClientImplementation = HttpClient(CIO)
        val noteGateway = NoteGateway(httpClientImplementation)
        val noteDataAccessObject = NoteDatabase().getInstance()
        val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

        return NotesListingViewModel(noteRepository)
    }
}