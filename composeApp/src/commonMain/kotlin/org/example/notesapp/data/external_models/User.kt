package org.example.notesapp.data.external_models

import kotlinx.serialization.Serializable
import org.example.notesapp.data.local_data_source.entities.UserEntity

@Serializable
data class User(val id: Int, val username: String) {
    fun getUserEntity() = UserEntity(id, username)
}