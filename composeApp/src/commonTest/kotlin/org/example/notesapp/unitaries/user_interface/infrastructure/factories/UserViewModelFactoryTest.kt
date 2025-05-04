package org.example.notesapp.unitaries.user_interface.infrastructure.factories

import org.example.notesapp.mocks.UserViewModelFactory
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class UserViewModelFactoryTest {
    private lateinit var userViewModelFactory: UserViewModelFactory

    @BeforeTest
    fun setUp() {
        userViewModelFactory = UserViewModelFactory()
    }

    @Test
    fun testIfMethodGetViewModelInstanceReturnsAWorkingInstance() {
        val instance = userViewModelFactory.getViewModelInstance()

        assertNotNull(instance)
    }
}