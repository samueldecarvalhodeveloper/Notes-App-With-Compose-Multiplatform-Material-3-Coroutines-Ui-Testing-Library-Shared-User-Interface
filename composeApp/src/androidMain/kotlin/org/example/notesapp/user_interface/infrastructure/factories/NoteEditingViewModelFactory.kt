package org.example.notesapp.user_interface.infrastructure.factories

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.example.notesapp.data.local_data_source.databases.NoteDatabase
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class NoteEditingViewModelFactory(private val context: Context) {
    actual fun getViewModelInstance(): NoteEditingViewModel {
        val httpClientImplementation = HttpClient(CIO)
        val noteGateway = NoteGateway(httpClientImplementation)
        val noteDataAccessObject = NoteDatabase(context).getInstance()
        val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

        return NoteEditingViewModel(noteRepository)
    }
}