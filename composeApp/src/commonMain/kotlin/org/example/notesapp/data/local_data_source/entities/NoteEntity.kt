package org.example.notesapp.data.local_data_source.entities

import org.example.notesapp.data.external_models.Note

class NoteEntity(
    var id: Int,
    var title: String,
    var body: String,
    var createdAt: Int,
    var updatedAt: Int,
    var userId: Int
) {
    fun getNoteExternalModel() = Note(
        id,
        title,
        body,
        createdAt,
        updatedAt,
        userId
    )
}