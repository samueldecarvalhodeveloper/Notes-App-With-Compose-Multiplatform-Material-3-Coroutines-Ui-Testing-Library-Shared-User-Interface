package org.example.notesapp.unitaries.user_interface.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import org.example.notesapp.user_interface.sections.LoadingSection
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class LoadingSectionTest {
    @Test
    fun testIfLoadingProgressIndicatorIsDisplayed() = runComposeUiTest {
        setContent {
            LoadingSection()
        }

        onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION)
            .assertIsDisplayed()
    }
}