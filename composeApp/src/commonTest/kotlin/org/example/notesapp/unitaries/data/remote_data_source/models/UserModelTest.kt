package org.example.notesapp.unitaries.data.remote_data_source.models

import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.remote_data_source.models.UserModel
import kotlin.test.Test
import kotlin.test.assertEquals

class UserModelTest {
    @Test
    fun testIfModelDescribesHowUserShouldHoldDataToTheService() {
        val userModel = UserModel(USER_USERNAME)

        assertEquals(USER_USERNAME, userModel.username)
    }
}