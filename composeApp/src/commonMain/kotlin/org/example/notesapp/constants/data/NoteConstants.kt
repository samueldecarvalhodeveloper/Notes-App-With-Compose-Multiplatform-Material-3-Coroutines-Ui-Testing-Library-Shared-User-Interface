package org.example.notesapp.constants.data

import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.data.local_data_source.entities.NoteEntity
import org.example.notesapp.data.remote_data_source.models.NoteModel

object NoteConstants {
    const val NOTE_ROUTE = "/notes/"

    const val NOTE_ID = 20

    const val NOTE_TITLE =
        "Title"

    const val NOTE_BODY =
        "Body"

    const val NOTE_CREATED_AT = 0

    const val NOTE_UPDATED_AT = 0

    val NOTE_MODEL_OBJECT = NoteModel(NOTE_TITLE, NOTE_BODY)

    val LIST_OF_NOTES_JSON = "[{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}]"

    val NOTE_JSON = "{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}"

    val NOTE_ENTITY_WITH_WRONG_DATA_OBJECT =
        NoteEntity(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    val NOTE_OBJECT_WITH_WRONG_DATA_OBJECT =
        Note(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    val NOTE_OBJECT =
        Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    val NOTE_ENTITY_OBJECT =
        NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)
}