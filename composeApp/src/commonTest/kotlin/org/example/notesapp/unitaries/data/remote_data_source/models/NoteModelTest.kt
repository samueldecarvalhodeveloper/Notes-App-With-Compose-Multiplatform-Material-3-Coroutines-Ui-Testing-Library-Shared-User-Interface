package org.example.notesapp.unitaries.data.remote_data_source.models

import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.data.remote_data_source.models.NoteModel
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteModelTest {
    @Test
    fun testIfModelDescribesHowNoteShouldHoldDataToTheService() {
        val noteModel = NoteModel(NOTE_TITLE, NOTE_BODY)

        assertEquals(NOTE_TITLE, noteModel.title)
        assertEquals(NOTE_BODY, noteModel.body)
    }
}