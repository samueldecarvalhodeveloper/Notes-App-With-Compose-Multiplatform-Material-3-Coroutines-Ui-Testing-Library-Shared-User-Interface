package org.example.notesapp.unitaries

import org.example.notesapp.MainViewController
import kotlin.test.Test
import kotlin.test.assertNotNull

class MainViewControllerTest {
    @Test
    fun testIfMainViewControllerSetsUpAllNeededDependenciesAndReturnsTheAWorkingViewController() {
        val mainViewController = MainViewController()

        assertNotNull(mainViewController)
    }
}