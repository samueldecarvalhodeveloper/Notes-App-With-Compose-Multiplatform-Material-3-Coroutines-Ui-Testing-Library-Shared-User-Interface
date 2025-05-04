package org.example.notesapp.unitaries.data.external_models

import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.external_models.Note
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteTest {
    @Test
    fun testIfEntityDescribesHowNoteShouldBeUsedByExternalDomains() {
        val note = Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        assertEquals(NOTE_ID.toLong(), note.id.toLong())
        assertEquals(NOTE_TITLE, note.title)
        assertEquals(NOTE_BODY, note.body)
        assertEquals(NOTE_CREATED_AT.toLong(), note.createdAt.toLong())
        assertEquals(NOTE_UPDATED_AT.toLong(), note.updatedAt.toLong())
        assertEquals(USER_ID.toLong(), note.userId.toLong())
    }

    @Test
    fun testIfMethodGetNoteEntityReturnsCastedDatabaseEntity() {
        val note = Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        val noteEntity = note.getNoteEntity()

        assertEquals(NOTE_ID.toLong(), noteEntity.id.toLong())
        assertEquals(NOTE_TITLE, noteEntity.title)
        assertEquals(NOTE_BODY, noteEntity.body)
        assertEquals(NOTE_CREATED_AT.toLong(), noteEntity.createdAt.toLong())
        assertEquals(NOTE_UPDATED_AT.toLong(), noteEntity.updatedAt.toLong())
        assertEquals(USER_ID.toLong(), noteEntity.userId.toLong())
    }
}