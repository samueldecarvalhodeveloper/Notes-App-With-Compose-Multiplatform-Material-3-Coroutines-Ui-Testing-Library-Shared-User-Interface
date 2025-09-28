package org.example.notesapp.user_interface.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.BACK_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.DELETE_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.EDIT_NOTE_CONTENT_DESCRIPTION
import org.example.notesapp.user_interface.theme.NEUTRALS_100
import org.example.notesapp.user_interface.theme.PRIMARY_500

@Composable
fun NoteManipulationAppTopBar(
    isManipulateNoteButtonAble: Boolean,
    isConcludeNoteButtonAble: Boolean,
    isDeleteNoteButtonAble: Boolean,
    onNavigationIconButtonClick: () -> Unit,
    onConcludeNoteIconButtonClick: () -> Unit,
    onEditNoteIconButtonClick: () -> Unit,
    onDeleteNoteIconButtonClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        backgroundColor = PRIMARY_500,
        contentColor = NEUTRALS_100,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
        navigationIcon = {
            IconButton(onClick = { onNavigationIconButtonClick() }) {
                Icon(
                    imageVector = Filled.KeyboardArrowLeft,
                    contentDescription = BACK_BUTTON_CONTENT_DESCRIPTION,
                    tint = NEUTRALS_100
                )
            }
        },
        actions = {
            if (isConcludeNoteButtonAble) {
                IconButton(onClick = { onConcludeNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = CONCLUDE_NOTE_BUTTON_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }

            if (isManipulateNoteButtonAble) {
                IconButton(onClick = { onEditNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = EDIT_NOTE_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }

            if (isDeleteNoteButtonAble) {
                IconButton(onClick = { onDeleteNoteIconButtonClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = DELETE_NOTE_CONTENT_DESCRIPTION,
                        tint = NEUTRALS_100
                    )
                }
            }
        }
    )
}