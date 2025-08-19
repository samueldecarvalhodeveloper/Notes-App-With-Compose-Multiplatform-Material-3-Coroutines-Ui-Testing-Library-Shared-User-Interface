package org.example.notesapp.unitaries.data.external_models

import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.external_models.User
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {
    @Test
    fun testIfEntityDescribesHowUserShouldBeUsedByExternalDomains() {
        val user = User(USER_ID, USER_USERNAME)

        assertEquals(USER_ID.toLong(), user.id.toLong())
        assertEquals(USER_USERNAME, user.username)
    }

    @Test
    fun testIfMethodGetUserEntityReturnsCastedDatabaseEntity() {
        val user = User(USER_ID, USER_USERNAME)

        val userEntity = user.getUserEntity()

        assertEquals(USER_ID.toLong(), userEntity.id.toLong())
        assertEquals(USER_USERNAME, userEntity.username)
    }
}