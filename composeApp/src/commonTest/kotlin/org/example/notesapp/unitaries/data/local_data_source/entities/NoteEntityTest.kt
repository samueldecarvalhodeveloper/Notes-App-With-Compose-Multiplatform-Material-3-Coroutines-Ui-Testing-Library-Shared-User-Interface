package org.example.notesapp.unitaries.data.local_data_source.entities

import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_CREATED_AT
import org.example.notesapp.constants.data.NoteConstants.NOTE_ID
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.NoteConstants.NOTE_UPDATED_AT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.local_data_source.entities.NoteEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteEntityTest {
    @Test
    fun testIfEntityDescribesHowNoteEntityShouldBeUsed() {
        val noteEntity =
            NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        assertEquals(noteEntity.id, NOTE_ID)
        assertEquals(noteEntity.title, NOTE_TITLE)
        assertEquals(noteEntity.body, NOTE_BODY)
        assertEquals(noteEntity.createdAt, NOTE_CREATED_AT)
        assertEquals(noteEntity.updatedAt, NOTE_UPDATED_AT)
        assertEquals(noteEntity.userId, USER_ID)
    }

    @Test
    fun testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        val noteEntity =
            NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        val note = noteEntity.getNoteExternalModel()

        assertEquals(note.id, NOTE_ID)
        assertEquals(note.title, NOTE_TITLE)
        assertEquals(note.body, NOTE_BODY)
        assertEquals(note.createdAt, NOTE_CREATED_AT)
        assertEquals(note.updatedAt, NOTE_UPDATED_AT)
        assertEquals(note.userId, USER_ID)
    }
}