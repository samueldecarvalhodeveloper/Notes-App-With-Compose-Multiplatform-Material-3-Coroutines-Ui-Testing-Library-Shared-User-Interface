package org.example.notesapp.user_interface.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
import org.example.notesapp.user_interface.theme.SECONDARY_500
import org.example.notesapp.user_interface.theme.SECONDARY_900

@Composable
fun CreateNoteFloatingActionButton(onNoteCreated: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(Filled.Create, null) },
        text = { Text(text = "Create note") },
        contentColor = SECONDARY_900,
        backgroundColor = SECONDARY_500,
        modifier = Modifier.semantics {
            contentDescription = CREATE_NOTE_FLOATING_ACTION_BUTTON_TEXT
        },
        onClick = {
            onNoteCreated()
        }
    )
}