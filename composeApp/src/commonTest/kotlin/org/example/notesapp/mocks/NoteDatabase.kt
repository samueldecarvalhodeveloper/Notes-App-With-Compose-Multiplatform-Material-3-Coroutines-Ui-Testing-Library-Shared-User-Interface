package org.example.notesapp.mocks

import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject

class NoteDatabase {
    fun getDataAccessObject(): NoteDataAccessObject {
        val noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

        return NoteDataAccessObject(noteQueries)
    }
}