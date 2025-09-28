package org.example.notesapp.data.external_models

import kotlinx.serialization.Serializable
import org.example.notesapp.data.local_data_source.entities.NoteEntity

@Serializable
data class Note(
    val id: Int,
    var title: String,
    var body: String,
    val createdAt: Int,
    val updatedAt: Int,
    val userId: Int
) {
    fun getNoteEntity() = NoteEntity(id, title, body, createdAt, updatedAt, userId)
}