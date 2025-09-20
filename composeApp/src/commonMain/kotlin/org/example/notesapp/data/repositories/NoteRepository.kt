package org.example.notesapp.data.repositories

import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.NoteGateway
import org.example.notesapp.data.remote_data_source.models.NoteModel

open class NoteRepository(
    private val noteGateway: NoteGateway,
    private val noteDataAccessObject: NoteDataAccessObject
) {
    open suspend fun fetchNotesFromService(userId: Int) {
        val notesFromService = noteGateway.getNotes(userId)
        val notesFromDatabase = noteDataAccessObject.getNotes()

        for (note in notesFromDatabase) {
            noteDataAccessObject.deleteNote(note.id)
        }

        for (note in notesFromService) {
            noteDataAccessObject.createNote(note.getNoteEntity())
        }
    }

    open suspend fun getNotes(): List<Note> {
        return noteDataAccessObject.getNotes()
            .map { noteEntity -> noteEntity.getNoteExternalModel() }
    }

    open suspend fun getNote(id: Int): Note {
        return noteDataAccessObject.getNote(id).getNoteExternalModel()
    }

    open suspend fun getCreatedNote(title: String, body: String, userId: Int): Note {
        val noteDataTransferObject = NoteModel(title, body)

        val createdNoteOnService = noteGateway.createNote(userId, noteDataTransferObject)

        noteDataAccessObject.createNote(createdNoteOnService.getNoteEntity())

        return createdNoteOnService
    }

    open suspend fun getUpdatedNote(id: Int, title: String, body: String, userId: Int): Note {
        val noteDataTransferObject = NoteModel(title, body)

        val updatedNoteOnService = noteGateway.updateNote(id, userId, noteDataTransferObject)

        noteDataAccessObject.updateNote(updatedNoteOnService.getNoteEntity())

        return updatedNoteOnService
    }

    open suspend fun deleteNote(id: Int, userId: Int) {
        noteGateway.deleteNote(id, userId)

        noteDataAccessObject.deleteNote(id)
    }
}