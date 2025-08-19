package org.example.notesapp.unitaries.data.local_data_source.databases

import kotlinx.coroutines.test.runTest
import org.example.notesapp.mocks.NoteDatabase
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class NoteDatabaseTest {
    private lateinit var noteDatabase: NoteDatabase

    @BeforeTest
    fun beforeEach() {
        noteDatabase = NoteDatabase()
    }

    @Test
    fun testIfMethodGetDataAccessObjectReturnsNoteDataAccessObjectImplementation() {
        runTest {
            val noteDataAccessObject = noteDatabase.getDataAccessObject()

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }
}
