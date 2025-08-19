package org.example.notesapp.unitaries.user_interface.view_models

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
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
import org.example.notesapp.mocks.NoteEditingViewModelMock
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import orgexamplenotesappdatalocaldatabase.NoteQueries
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NoteEditingViewModelTest {
    private lateinit var noteEditingViewModel: NoteEditingViewModel
    private lateinit var noteRepository: NoteRepository
    private lateinit var noteGateway: NoteGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine
    private lateinit var noteDataAccessObject: NoteDataAccessObject
    private lateinit var noteQueries: NoteQueries

    @Test
    fun testIfMethodLoadNoteReturnsNoteFromDatabaseAndSetsCurrentNoteBeingEditedAndTurnsIsNoteLoadedToTrue() {
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

            noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            noteEditingViewModel.loadNote(NOTE_ID)

            val noteFromDatabase = noteEditingViewModel.noteState.value!!

            val isNoteLoaded = noteEditingViewModel.isNoteLoadedState.value

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)

            assertTrue(isNoteLoaded)
        }
    }

    @Test
    fun testIfMethodManipulateNoteTurnsIsNoteBeingManipulatedToTrueIfIsNoteManipulationAbleIsFalse() {
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

        noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

        noteEditingViewModel.manipulateNote()

        val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value

        assertTrue(isNoteBeingManipulated)
    }

    @Test
    fun testIfMethodSetTitleSetsTheNoteBeingManipulatedTitle() {
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

        noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

        noteEditingViewModel.setNoteTitle(NOTE_TITLE)

        val noteTitleBeingManipulated =
            (noteEditingViewModel as NoteEditingViewModelMock).noteTitleBeingManipulated

        assertEquals(NOTE_TITLE, noteTitleBeingManipulated)
    }

    @Test
    fun testIfMethodSetBodySetsTheNoteBeingManipulatedBody() {
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

        noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

        noteEditingViewModel.setNoteBody(NOTE_BODY)

        val noteBodyBeingManipulated =
            (noteEditingViewModel as NoteEditingViewModelMock).noteBodyBeingManipulated

        assertEquals(NOTE_BODY, noteBodyBeingManipulated)
    }

    @Test
    fun testIfMethodConcludeNoteUpdatesNoteOnServiceAndOnDatabaseAndTurnsIsNoteBeingManipulatedToFalseAndUpdatesNoteState() {
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

            noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

            noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT)

            noteEditingViewModel.loadNote(NOTE_ID)

            noteEditingViewModel.setNoteTitle(NOTE_TITLE)
            noteEditingViewModel.setNoteBody(NOTE_BODY)

            noteEditingViewModel.concludeNote()

            val noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID)
            val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value
            val noteState = noteEditingViewModel.noteState.value!!

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)

            assertFalse(isNoteBeingManipulated)

            assertEquals(NOTE_ID, noteState.id)
            assertEquals(NOTE_TITLE, noteState.title)
            assertEquals(NOTE_BODY, noteState.body)
            assertEquals(NOTE_CREATED_AT, noteState.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteState.updatedAt)
            assertEquals(USER_ID, noteState.userId)
        }
    }

    @Test
    fun testIfMethodConcludeNoteOnFailingTurnsIsNoteManipulationAbleToTrueIsNoteBeingManipulatedToFalse() {
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

        noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

        noteEditingViewModel.concludeNote()

        val isNoteManipulationAble = noteEditingViewModel.isNoteManipulationAbleState.value
        val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value

        assertFalse(isNoteManipulationAble)
        assertFalse(isNoteBeingManipulated)
    }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnServiceAndOnDatabaseAndTurnsIsNoteDeletedToTrue() {
        runTest {
            var isNoteDeleted = false
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

            noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

            noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT)

            noteEditingViewModel.loadNote(NOTE_ID)

            noteEditingViewModel.deleteNote {
                isNoteDeleted = true
            }

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())

            assertTrue(isNoteDeleted)
        }
    }

    @Test
    fun testIfMethodDeleteNoteTurnsIsNoteBeingManipulatedToFalseAndIsNoteManipulationAbleToTrue() {
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

        noteEditingViewModel = NoteEditingViewModelMock(noteRepository)

        noteEditingViewModel.deleteNote {}

        val isNoteBeingManipulated = noteEditingViewModel.isNoteBeingManipulatedState.value!!
        val isNoteManipulationAble = noteEditingViewModel.isNoteManipulationAbleState.value!!

        assertFalse(isNoteBeingManipulated)
        assertFalse(isNoteManipulationAble)
    }
}
