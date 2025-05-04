package org.example.notesapp.unitaries.data.remote_data_source.services

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.NoteConstants.LIST_OF_NOTES_JSON
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.remote_data_source.services.NoteService
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteServiceTest {
    private lateinit var noteService: NoteService
    private lateinit var httpClientEngineMock: MockEngine

    @BeforeTest
    fun beforeEach() {
        httpClientEngineMock = MockEngine {
            respond(
                content = ByteReadChannel(LIST_OF_NOTES_JSON),
                status = OK,
                headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
            )
        }

        noteService = NoteService()
    }

    @Test
    fun testIfMethodGetInstanceReturnsWorkingInstance() {
        runTest {
            val noteGateway = noteService.getInstance(httpClientEngineMock)

            val noteFromService = noteGateway.getNotes(USER_ID).first()

            assertEquals(NOTE_ID, noteFromService.id)
            assertEquals(NOTE_TITLE, noteFromService.title)
            assertEquals(NOTE_BODY, noteFromService.body)
            assertEquals(NOTE_CREATED_AT, noteFromService.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromService.updatedAt)
            assertEquals(USER_ID, noteFromService.userId)
        }
    }
}