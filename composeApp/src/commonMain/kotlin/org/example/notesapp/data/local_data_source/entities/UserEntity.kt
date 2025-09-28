package org.example.notesapp.data.local_data_source.entities

import org.example.notesapp.data.external_models.User

class UserEntity(var id: Int, var username: String) {
    fun getUserExternalModel() = User(id, username)
}