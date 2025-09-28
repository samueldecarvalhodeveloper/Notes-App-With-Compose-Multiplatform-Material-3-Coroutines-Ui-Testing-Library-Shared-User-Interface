package org.example.notesapp.data.local_data_source.data_access_objects

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.notesapp.data.local_data_source.entities.UserEntity
import orgexamplenotesappdatalocaldatabase.UserQueries

class UserDataAccessObject(private var userQueries: UserQueries) {
    suspend fun getUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext userQueries.getUsers().executeAsList().map {
                UserEntity(it.id.toInt(), it.username)
            }
        }
    }

    suspend fun createUser(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userQueries.createUser(user.id.toLong(), user.username)
        }
    }
}