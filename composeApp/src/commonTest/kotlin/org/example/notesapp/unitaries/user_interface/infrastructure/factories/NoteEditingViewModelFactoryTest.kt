package org.example.notesapp.unitaries.user_interface.infrastructure.factories

import org.example.notesapp.mocks.NoteEditingViewModelFactory
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class NoteEditingViewModelFactoryTest {
    private lateinit var noteEditingViewModelFactory: NoteEditingViewModelFactory

    @BeforeTest
    fun setUp() {
        noteEditingViewModelFactory = NoteEditingViewModelFactory()
    }

    @Test
    fun testIfMethodGetViewModelInstanceReturnsAWorkingInstance() {
        val instance = noteEditingViewModelFactory.getViewModelInstance()

        assertNotNull(instance)
    }
}