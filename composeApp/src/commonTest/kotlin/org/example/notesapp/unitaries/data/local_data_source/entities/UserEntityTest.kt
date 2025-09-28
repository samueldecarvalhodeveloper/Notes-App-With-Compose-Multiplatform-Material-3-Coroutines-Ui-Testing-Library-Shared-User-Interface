package org.example.notesapp.unitaries.data.local_data_source.entities

import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.local_data_source.entities.UserEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class UserEntityTest {
    @Test
    fun testIfEntityDescribesHowUserEntityShouldBeUsed() {
        val userEntity = UserEntity(USER_ID, USER_USERNAME)

        assertEquals(userEntity.id, USER_ID)
        assertEquals(userEntity.username, USER_USERNAME)
    }

    @Test
    fun testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        val userEntity = UserEntity(USER_ID, USER_USERNAME)

        val user = userEntity.getUserExternalModel()

        assertEquals(user.id, USER_ID)
        assertEquals(user.username, USER_USERNAME)
    }
}