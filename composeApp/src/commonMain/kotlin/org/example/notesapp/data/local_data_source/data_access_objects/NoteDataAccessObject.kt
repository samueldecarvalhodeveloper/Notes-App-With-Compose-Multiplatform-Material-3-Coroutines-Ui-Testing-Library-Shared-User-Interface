package org.example.notesapp.data.local_data_source.data_access_objects

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.notesapp.data.local_data_source.entities.NoteEntity
import orgexamplenotesappdatalocaldatabase.NoteQueries

class NoteDataAccessObject(private val noteQueries: NoteQueries) {
    suspend fun getNotes(): List<NoteEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext noteQueries.getNotes().executeAsList().map {
                NoteEntity(
                    it.id.toInt(),
                    it.title,
                    it.body,
                    it.createdAt.toInt(),
                    it.updatedAt.toInt(),
                    it.userId.toInt()
                )
            }
        }
    }

    suspend fun getNote(id: Int): NoteEntity {
        return withContext(Dispatchers.IO) {
            val noteFromDatabase = noteQueries.getNote(id.toLong()).executeAsList().first()

            return@withContext NoteEntity(
                noteFromDatabase.id.toInt(),
                noteFromDatabase.title,
                noteFromDatabase.body,
                noteFromDatabase.createdAt.toInt(),
                noteFromDatabase.updatedAt.toInt(),
                noteFromDatabase.userId.toInt()
            )
        }
    }

    suspend fun createNote(note: NoteEntity) {
        withContext(Dispatchers.IO) {
            noteQueries.createNote(
                note.id.toLong(),
                note.title,
                note.body,
                note.createdAt.toLong(),
                note.updatedAt.toLong(),
                note.userId.toLong(),
            )
        }
    }

    suspend fun updateNote(note: NoteEntity) {
        withContext(Dispatchers.IO) {
            noteQueries.updateNote(
                note.id.toLong(),
                note.title,
                note.body,
                note.createdAt.toLong(),
                note.updatedAt.toLong(),
                note.userId.toLong(),
                note.id.toLong(),
            )
        }
    }

    suspend fun deleteNote(id: Int) {
        withContext(Dispatchers.IO) {
            noteQueries.deleteNote(id.toLong())
        }
    }
}