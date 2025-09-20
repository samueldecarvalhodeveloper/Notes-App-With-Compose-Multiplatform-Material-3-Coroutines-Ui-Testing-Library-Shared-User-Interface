package org.example.notesapp.constants.user_interface

object UserInterfaceConstants {
    const val USER_SIGN_IN_SCREEN = "usersigninscreen"

    const val NOTES_LISTING_SCREEN = "noteslistingscreen"

    const val NOTE_MANIPULATING_SCREEN = "notemanipulatingscreen/{noteid}"

    const val NOTE_ID_ARGUMENT_KEY = "{noteid}"

    const val NOTE_ID_ARGUMENT = "noteid"

    const val CREATE_USER_BUTTON_TEXT = "Create user"

    const val NOT_VALID_USERNAME_ERROR_MESSAGE = "Not valid username"

    const val NOT_AVAILABLE_INTERNET_ERROR_MESSAGE = "No internet connection"

    const val USERNAME_TEXT_INPUT_LABEL = "Username"

    const val LOADING_SECTION_CONTENT_DESCRIPTION = "loadingsectioncontentdescription"

    fun TOP_BAR_GREETING_TITLE_TEXT(userUsername: String) = "Hi, $userUsername"

    const val NO_NOTES_LABEL_TEXT = "No notes"

    const val CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT = "Create note"

    const val BACK_BUTTON_CONTENT_DESCRIPTION = "backbuttoncontentdescription"

    const val CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION = "concludebuttoncontentdescription"

    const val EDIT_NOTE_CONTENT_DESCRIPTION = "editnotecontentdescription"

    const val DELETE_NOTE_CONTENT_DESCRIPTION = "deletenotecontentdescription"

    const val TEXT_INPUT_PLACEHOLDER_TEXT = "placeholder"

    const val DATA_FETCHING_DELAY = 100L
}