package org.example.notesapp.data.remote_data_source.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway

class NoteService {
    fun getInstance(httpClientEngineMock: HttpClientEngine): NoteGateway {
        val httpClientImplementation = HttpClient(httpClientEngineMock) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }

        return NoteGateway(httpClientImplementation)
    }
}