package org.example.notesapp.unitaries.user_interface.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import org.example.notesapp.constants.data.NoteConstants.NOTE_BODY
import org.example.notesapp.constants.data.NoteConstants.NOTE_OBJECT
import org.example.notesapp.constants.data.NoteConstants.NOTE_TITLE
import org.example.notesapp.constants.data.UserConstants.USER_EXTERNAL_MODEL_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NO_NOTES_LABEL_TEXT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.TOP_BAR_GREETING_TITLE_TEXT
import org.example.notesapp.data.repositories.NoteRepository
import org.example.notesapp.mocks.NotesListingViewModelMock
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.screens.NotesListingScreen
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NotesListingScreenTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var notesListingViewModel: NotesListingViewModel
    private lateinit var noteRepository: NoteRepository

    @BeforeTest
    fun beforeEach() {
        userViewModel = mock<UserViewModelMock>(autofill)
        notesListingViewModel = mock<NotesListingViewModelMock>(autofill)
        noteRepository = mock<NoteRepository>()
    }

    @Test
    fun testIfLoadingSectionIsDisplayedWhenListOfNotesIsNotLoaded() = runComposeUiTest {
        every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (
                mutableStateOf(
                    true
                )
                )
        every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(false))
        every { notesListingViewModel.listOfNotesState } returns (mutableStateOf(listOf()))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = {})
        }

        onNodeWithContentDescription(LOADING_SECTION_CONTENT_DESCRIPTION).assertIsDisplayed()
    }

    @Test
    fun testIfUsernameIsDisplayedOnTopBarGreeting() = runComposeUiTest {
        every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (
                mutableStateOf(
                    true
                )
                )
        every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(false))
        every { notesListingViewModel.listOfNotesState } returns (mutableStateOf(listOf()))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = {})
        }

        onNodeWithText(TOP_BAR_GREETING_TITLE_TEXT(USER_USERNAME)).assertIsDisplayed()
    }

    @Test
    fun testIfNoNotesSectionIsDisplayedWhenListOfNotesIsEmpty() = runComposeUiTest {
        every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (
                mutableStateOf(
                    true
                )
                )
        every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(true))
        every { notesListingViewModel.listOfNotesState } returns (mutableStateOf(listOf()))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = {})
        }

        onNodeWithText(NO_NOTES_LABEL_TEXT).assertIsDisplayed()
    }

    @Test
    fun testIfListOfNotesIsLoadedOnComponentAfterNotesFetchingFromServiceAndStoringOnDatabaseCompositionAndIsDisplayed() =
        runComposeUiTest {
            every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
            every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (
                    mutableStateOf(
                        true
                    )
                    )
            every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(true))
            every { notesListingViewModel.listOfNotesState } returns (
                    mutableStateOf(
                        listOf(
                            NOTE_OBJECT
                        )
                    )
                    )

            setContent {
                NotesListingScreen(
                    notesListingViewModel = notesListingViewModel,
                    userViewModel = userViewModel,
                    onNoteSelected = {})
            }

            onNodeWithText(NOTE_TITLE).assertIsDisplayed()
            onNodeWithText(NOTE_BODY).assertIsDisplayed()

            onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT).assertIsDisplayed()
        }

    @Test
    fun testIfNavigatesToNoteManipulatingScreenWhenNoteItemFromListOfNotesIsClicked() =
        runComposeUiTest {
            var isNoteSelected = false

            every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
            every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (
                    mutableStateOf(
                        true
                    )
                    )
            every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(true))
            every { notesListingViewModel.listOfNotesState } returns (
                    mutableStateOf(
                        listOf(
                            NOTE_OBJECT
                        )
                    )
                    )

            setContent {
                NotesListingScreen(
                    notesListingViewModel = notesListingViewModel,
                    userViewModel = userViewModel,
                    onNoteSelected = { isNoteSelected = true })
            }

            onNodeWithText(NOTE_TITLE).performClick()

            assertTrue(isNoteSelected)
        }

    @Test
    fun testIfCreateNoteFloatingActionButtonIsNotDisplayedWhenNoteCreationCurrentlyAbleIsFalse() =
        runComposeUiTest {
            every { userViewModel.userState } returns (mutableStateOf(USER_EXTERNAL_MODEL_OBJECT))
            every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns (mutableStateOf(
                false
            ))
            every { notesListingViewModel.isListOfNotesLoadedState } returns (mutableStateOf(true))
            every { notesListingViewModel.listOfNotesState } returns (mutableStateOf(
                listOf(
                    NOTE_OBJECT
                )
            ))

            setContent {
                NotesListingScreen(
                    notesListingViewModel = notesListingViewModel,
                    userViewModel = userViewModel,
                    onNoteSelected = {}
                )
            }

            onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
                .assertIsNotDisplayed()
        }

    @Test
    fun testIfNavigatesToNoteManipulatingScreenWhenNoteIsCreated() = runComposeUiTest {
        var isNoteCreated = false

        every { notesListingViewModel.createNote(any(), any()) } calls { isNoteCreated = true }

        every { userViewModel.userState } returns mutableStateOf(USER_EXTERNAL_MODEL_OBJECT)
        every { notesListingViewModel.isNoteCreationCurrentlyAbleState } returns mutableStateOf(
            true
        )
        every { notesListingViewModel.isListOfNotesLoadedState } returns mutableStateOf(true)
        every { notesListingViewModel.listOfNotesState } returns mutableStateOf(listOf(NOTE_OBJECT))

        setContent {
            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel,
                onNoteSelected = { isNoteCreated = true }
            )
        }

        onNodeWithContentDescription(CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT)
            .performClick()

        assertTrue(isNoteCreated)
    }
}