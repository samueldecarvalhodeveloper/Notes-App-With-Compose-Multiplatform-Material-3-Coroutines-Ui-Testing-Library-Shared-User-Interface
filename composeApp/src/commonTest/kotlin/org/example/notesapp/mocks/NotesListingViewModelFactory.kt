package org.example.notesapp.mocks

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngine.Companion.invoke
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.NoteConstants.LIST_OF_NOTES_JSON
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.user_interface.view_models.NotesListingViewModel

class NotesListingViewModelFactory {
    fun getViewModelInstance(): NotesListingViewModel {
        val httpClientEngineMock = MockEngine {
            respond(
                content = ByteReadChannel(LIST_OF_NOTES_JSON),
                status = OK,
                headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
            )
        }
        val httpClientImplementation = HttpClient(httpClientEngineMock) {
            install(ContentNegotiation) {
                json()
            }
        }
        val noteGateway = NoteGateway(httpClientImplementation)

        val noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

        val noteDataAccessObject = NoteDataAccessObject(noteQueries)

        val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

        return NotesListingViewModelMock(noteRepository)
    }
}