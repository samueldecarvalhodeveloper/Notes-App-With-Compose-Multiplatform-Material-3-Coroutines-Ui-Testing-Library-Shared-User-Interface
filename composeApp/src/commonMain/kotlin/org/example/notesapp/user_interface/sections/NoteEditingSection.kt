package org.example.notesapp.user_interface.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.user_interface.components.TextInput
import org.example.notesapp.user_interface.theme.NEUTRALS_300
import org.example.notesapp.user_interface.theme.NEUTRALS_900

@Composable
fun NoteEditingSection(
    note: Note,
    innerPadding: PaddingValues,
    onNoteTitleChange: (String) -> Unit,
    onNoteBodyChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TextInput(
            value = note.title,
            placeholder = "Title",
            singleLine = true,
            textColor = NEUTRALS_900,
            placeholderColor = NEUTRALS_300,
            fontSize = 40.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 16.dp)
                .fillMaxWidth(),
            onValueChange = {
                onNoteTitleChange(it)
            }
        )
        TextInput(
            value = note.body,
            placeholder = "Body",
            singleLine = false,
            textColor = NEUTRALS_900,
            placeholderColor = NEUTRALS_300,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 16.dp)
                .fillMaxSize(),
            onValueChange = {
                onNoteBodyChange(it)
            },
        )
    }
}