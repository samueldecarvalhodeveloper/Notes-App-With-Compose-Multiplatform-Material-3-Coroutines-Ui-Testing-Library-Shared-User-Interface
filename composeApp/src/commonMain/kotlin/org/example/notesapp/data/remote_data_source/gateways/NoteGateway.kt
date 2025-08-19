package org.example.notesapp.data.remote_data_source.gateways

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import org.example.notesapp.constants.data.NoteConstants.NOTE_ROUTE
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.remote_data_source.models.NoteModel

class NoteGateway(private val httpClientImplementation: HttpClient) {
    suspend fun getNotes(userId: Int): List<Note> {
        return withContext(Dispatchers.IO) {
            return@withContext httpClientImplementation.get("${SERVICE_URL}${NOTE_ROUTE}${userId}/")
                .body()
        }
    }

    suspend fun createNote(userId: Int, note: NoteModel): Note {
        return withContext(Dispatchers.IO) {
            return@withContext httpClientImplementation
                .post("${SERVICE_URL}${NOTE_ROUTE}${userId}/") {
                    contentType(Json)
                    setBody(note)
                }.body()
        }
    }

    suspend fun updateNote(noteId: Int, userId: Int, note: NoteModel): Note {
        return withContext(Dispatchers.IO) {
            return@withContext httpClientImplementation
                .post("${SERVICE_URL}${NOTE_ROUTE}${userId}/${noteId}/") {
                    contentType(Json)
                    setBody(note)
                }.body()
        }
    }

    suspend fun deleteNote(noteId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            httpClientImplementation.delete("${SERVICE_URL}${NOTE_ROUTE}${userId}/${noteId}/")
        }
    }
}