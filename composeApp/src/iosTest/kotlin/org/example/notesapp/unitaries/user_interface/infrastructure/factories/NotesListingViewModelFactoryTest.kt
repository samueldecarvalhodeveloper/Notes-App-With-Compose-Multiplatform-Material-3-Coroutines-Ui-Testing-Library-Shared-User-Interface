package org.example.notesapp.unitaries.user_interface.infrastructure.factories

import org.example.notesapp.mocks.NotesListingViewModelFactory
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class NotesListingViewModelFactoryTest {
    private lateinit var notesListingViewModelFactory: NotesListingViewModelFactory

    @BeforeTest
    fun setUp() {
        notesListingViewModelFactory = NotesListingViewModelFactory()
    }

    @Test
    fun testIfMethodGetViewModelInstanceReturnsAWorkingInstance() {
        val instance = notesListingViewModelFactory.getViewModelInstance()

        assertNotNull(instance)
    }
}