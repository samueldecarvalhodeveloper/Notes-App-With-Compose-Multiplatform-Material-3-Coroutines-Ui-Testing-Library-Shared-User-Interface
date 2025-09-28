package org.example.notesapp.data.repositories

import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.UserGateway
import org.example.notesapp.data.remote_data_source.models.UserModel

open class UserRepository(
    private val userGateway: UserGateway,
    private val userDataAccessObject: UserDataAccessObject
) {
    open suspend fun getUser(): User {
        return userDataAccessObject.getUsers().last().getUserExternalModel()
    }

    open suspend fun getCreatedUser(username: String): User {
        val userDataTransferObject = UserModel(username)

        val createdUserOnService = userGateway.createUser(userDataTransferObject)

        userDataAccessObject.createUser(createdUserOnService.getUserEntity())

        return createdUserOnService
    }
}

