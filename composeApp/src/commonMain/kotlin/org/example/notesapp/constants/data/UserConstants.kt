package org.example.notesapp.constants.data

import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.local_data_source.entities.UserEntity
import org.example.notesapp.data.remote_data_source.models.UserModel

object UserConstants {
    const val USER_ROUTE = "/users/"

    const val USER_ID = 10

    const val USER_USERNAME = "Samuel de Carvalho"

    val USER_MODEL_OBJECT = UserModel(USER_USERNAME)

    const val USER_JSON = "{\"id\":" + USER_ID + ",\"username\":\"" + USER_USERNAME + "\"}"

    val USER_ENTITY_OBJECT = UserEntity(USER_ID, USER_USERNAME)

    val USER_EXTERNAL_MODEL_OBJECT = User(USER_ID, USER_USERNAME)
}