package org.example.notesapp.components

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
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.NoteConstants.LIST_OF_NOTES_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ENTITY_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ENTITY_WITH_WRONG_DATA_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.repositories.NoteRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import orgexamplenotesappdatalocaldatabase.NoteQueries

class NoteTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteGateway: NoteGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine
    private lateinit var noteDataAccessObject: NoteDataAccessObject
    private lateinit var noteQueries: NoteQueries

    @Test
    fun fetchingNotesFromServiceAndStoringOnDatabase() {
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            noteRepository.fetchNotesFromService(USER_ID)

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isNotEmpty())
        }
    }

    @Test
    fun fetchingNotesFromDatabase() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(""),
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            val notesFromDatabase = noteRepository.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }

    @Test
    fun fetchingNoteFromDatabase() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(""),
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            val noteFromDatabase = noteRepository.getNote(NOTE_ID)

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)
        }
    }

    @Test
    fun creatingNoteOnServiceAndOnDatabase() {
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            val createdNoteFromDatabase =
                noteRepository.getCreatedNote(NOTE_TITLE, NOTE_BODY, USER_ID)

            assertEquals(NOTE_ID, createdNoteFromDatabase.id)
            assertEquals(NOTE_TITLE, createdNoteFromDatabase.title)
            assertEquals(NOTE_BODY, createdNoteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, createdNoteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, createdNoteFromDatabase.updatedAt)
            assertEquals(USER_ID, createdNoteFromDatabase.userId)
        }
    }

    @Test
    fun updatingNoteOnServiceAndOnDatabase() {
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT)

            val updatedNoteFromDatabase =
                noteRepository.getUpdatedNote(NOTE_ID, NOTE_TITLE, NOTE_BODY, USER_ID)

            assertEquals(NOTE_ID, updatedNoteFromDatabase.id)
            assertEquals(NOTE_TITLE, updatedNoteFromDatabase.title)
            assertEquals(NOTE_BODY, updatedNoteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, updatedNoteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, updatedNoteFromDatabase.updatedAt)
            assertEquals(USER_ID, updatedNoteFromDatabase.userId)
        }
    }

    @Test
    fun deletingNoteOnServiceAndOnDatabase() {
        runTest {
            httpClientEngineMock = MockEngine {
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

            noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

            noteDataAccessObject = NoteDataAccessObject(noteQueries)

            noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            noteRepository.deleteNote(NOTE_ID, USER_ID)

            val notesFromDatabase = noteRepository.getNotes()

            assert(notesFromDatabase.isEmpty())
        }
    }
}