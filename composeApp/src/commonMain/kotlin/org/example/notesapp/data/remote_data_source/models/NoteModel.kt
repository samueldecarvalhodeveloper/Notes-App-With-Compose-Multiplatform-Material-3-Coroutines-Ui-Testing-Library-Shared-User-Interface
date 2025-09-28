package org.example.notesapp.data.remote_data_source.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteModel(val title: String, val body: String)