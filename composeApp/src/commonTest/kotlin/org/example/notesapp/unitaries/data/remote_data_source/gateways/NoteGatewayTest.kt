package org.example.notesapp.unitaries.data.remote_data_source.gateways

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.NoteConstants.LIST_OF_NOTES_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_MODEL_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NoteGatewayTest {
    private lateinit var noteGateway: NoteGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine

    @Test
    fun testIfMethodGetNotesSuccessFetchAllUserNotesFromService() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(LIST_OF_NOTES_JSON),
                    status = OK,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            noteGateway = NoteGateway(httpClientImplementation)

            val noteFromService = noteGateway.getNotes(USER_ID).first()

            assertEquals(NOTE_ID, noteFromService.id)
            assertEquals(NOTE_TITLE, noteFromService.title)
            assertEquals(NOTE_BODY, noteFromService.body)
            assertEquals(NOTE_CREATED_AT, noteFromService.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromService.updatedAt)
            assertEquals(USER_ID, noteFromService.userId)
        }
    }

    @Test
    fun testIfMethodCreateNoteCreatesNotesOnService() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(NOTE_JSON),
                    status = Created,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            noteGateway = NoteGateway(httpClientImplementation)

            val createdNoteOnService = noteGateway.createNote(USER_ID, NOTE_MODEL_OBJECT)

            assertEquals(NOTE_ID, createdNoteOnService.id)
            assertEquals(NOTE_TITLE, createdNoteOnService.title)
            assertEquals(NOTE_BODY, createdNoteOnService.body)
            assertEquals(NOTE_CREATED_AT, createdNoteOnService.createdAt)
            assertEquals(NOTE_UPDATED_AT, createdNoteOnService.updatedAt)
            assertEquals(USER_ID, createdNoteOnService.userId)
        }
    }

    @Test
    fun testIfMethodUpdateNoteUpdatesNotesOnService() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(NOTE_JSON),
                    status = OK,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            noteGateway = NoteGateway(httpClientImplementation)

            val updatedNoteFromService = noteGateway.updateNote(NOTE_ID, USER_ID, NOTE_MODEL_OBJECT)

            assertEquals(NOTE_ID, updatedNoteFromService.id)
            assertEquals(NOTE_TITLE, updatedNoteFromService.title)
            assertEquals(NOTE_BODY, updatedNoteFromService.body)
            assertEquals(NOTE_CREATED_AT, updatedNoteFromService.createdAt)
            assertEquals(NOTE_UPDATED_AT, updatedNoteFromService.updatedAt)
            assertEquals(USER_ID, updatedNoteFromService.userId)
        }
    }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnService() {
        var isNoteDeleted = false

        runTest {
            httpClientEngineMock = MockEngine {
                isNoteDeleted = true

                respond(
                    content = ByteReadChannel(""),
                    status = NoContent,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            noteGateway = NoteGateway(httpClientImplementation)

            noteGateway.deleteNote(NOTE_ID, USER_ID)

            assertTrue(isNoteDeleted)
        }
    }
}