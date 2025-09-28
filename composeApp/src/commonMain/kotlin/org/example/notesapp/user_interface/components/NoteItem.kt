package org.example.notesapp.user_interface.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.user_interface.theme.NEUTRALS_200
import org.example.notesapp.user_interface.theme.NEUTRALS_500
import org.example.notesapp.user_interface.theme.NEUTRALS_900

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = NEUTRALS_900,
                maxLines = 1,
                overflow = Ellipsis
            )
            Text(
                text = note.body,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                color = NEUTRALS_500,
                maxLines = 2,
                overflow = Ellipsis
            )
        }
        Divider(
            thickness = 1.dp,
            color = NEUTRALS_200
        )
    }
}
