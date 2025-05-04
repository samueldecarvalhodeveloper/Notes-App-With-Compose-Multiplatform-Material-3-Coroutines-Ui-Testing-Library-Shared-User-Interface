package org.example.notesapp.unitaries.user_interface.view_models

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.NoteConstants.LIST_OF_NOTES_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_JSON
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import orgexamplenotesappdatalocaldatabase.NoteQueries
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NotesListingViewModelTest {
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteGateway: NoteGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine
    private lateinit var noteDataAccessObject: NoteDataAccessObject
    private lateinit var noteQueries: NoteQueries

    @Test
    fun testIfMethodLoadNotesRetrievesUserNotesFromServiceAndStoresThemOnDatabaseAndAddsThemToListOfNotesState() {
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

            notesListingViewModel = NotesListingViewModelMock(noteRepository)

            notesListingViewModel.loadNotes(USER_ID)

            val listOfNotesFromDatabase = noteDataAccessObject.getNotes()

            val listOfNotesState =
                notesListingViewModel.listOfNotesState.value
            val isListOfNotesLoaded =
                notesListingViewModel.isListOfNotesLoadedState.value

            assertTrue(listOfNotesFromDatabase.isNotEmpty())

            assertFalse(listOfNotesState.isEmpty())

            assertTrue(isListOfNotesLoaded)
        }
    }

    @Test
    fun testIfMethodCreateNoteCreatesNoteOnServiceAndAddsItToCreatedNoteState() {
        runTest {
            var isNoteCreated = false
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

            notesListingViewModel = NotesListingViewModelMock(noteRepository)

            notesListingViewModel.createNote(USER_ID) {
                isNoteCreated = true
            }

            assertTrue(isNoteCreated)
        }
    }

    @Test
    fun testIfMethodCreateNoteTurnsIsNoteCreationCurrentlyAbleToIfNoteCreationFails() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(""),
                    status = NotFound,
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

            notesListingViewModel = NotesListingViewModelMock(noteRepository)

            notesListingViewModel.createNote(USER_ID) {
            }

            val isNoteCreationCurrentlyAble =
                notesListingViewModel.isNoteCreationCurrentlyAbleState.value

            assertFalse(isNoteCreationCurrentlyAble)
        }
    }
}