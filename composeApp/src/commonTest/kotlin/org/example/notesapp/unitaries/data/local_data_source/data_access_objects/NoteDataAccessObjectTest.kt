package org.example.notesapp.unitaries.data.local_data_source.data_access_objects

import kotlinx.coroutines.test.runTest
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ENTITY_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import orgexamplenotesappdatalocaldatabase.NoteQueries
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NoteDataAccessObjectTest {
    private lateinit var noteDataAccessObject: NoteDataAccessObject
    private lateinit var noteQueries: NoteQueries

    @BeforeTest
    fun beforeEach() {
        noteQueries = DatabaseQueriesFactory.getNoteQueriesInstance()

        noteDataAccessObject = NoteDataAccessObject(noteQueries)
    }

    @Test
    fun testIfMethodGetNotesReturnsAllNotesFromDatabase() {
        runTest {
            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }

    @Test
    fun testIfMethodGetNoteReturnsNoteFromDatabase() {
        runTest {
            noteQueries.createNote(
                NOTE_ID.toLong(),
                NOTE_TITLE,
                NOTE_BODY,
                NOTE_CREATED_AT.toLong(),
                NOTE_UPDATED_AT.toLong(),
                USER_ID.toLong()
            )

            val noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID)

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)
        }
    }

    @Test
    fun testIfMethodCreateNoteCreatesNoteOnDatabase() {
        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            val noteFromDatabase = noteQueries.getNote(NOTE_ID.toLong()).executeAsList().first()

            assertEquals(NOTE_ID.toLong(), noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT.toLong(), noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT.toLong(), noteFromDatabase.updatedAt)
            assertEquals(USER_ID.toLong(), noteFromDatabase.userId)
        }
    }

    @Test
    fun testIfMethodUpdateNoteUpdatesNoteOnDatabase() {
        runTest {
            noteQueries.createNote(
                NOTE_ID.toLong(),
                NOTE_TITLE,
                NOTE_BODY,
                NOTE_CREATED_AT.toLong(),
                NOTE_UPDATED_AT.toLong(),
                USER_ID.toLong()
            )

            noteDataAccessObject.updateNote(NOTE_ENTITY_OBJECT)

            val noteFromDatabase = noteQueries.getNotes().executeAsList().first()

            assertEquals(NOTE_ID.toLong(), noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT.toLong(), noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT.toLong(), noteFromDatabase.updatedAt)
            assertEquals(USER_ID.toLong(), noteFromDatabase.userId)
        }
    }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnDatabase() {
        runTest {
            noteQueries.createNote(
                NOTE_ID.toLong(),
                NOTE_TITLE,
                NOTE_BODY,
                NOTE_CREATED_AT.toLong(),
                NOTE_UPDATED_AT.toLong(),
                USER_ID.toLong()
            )

            noteDataAccessObject.deleteNote(NOTE_ID)

            val notesFromDatabase = noteQueries.getNotes().executeAsList()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }
}