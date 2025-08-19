package org.example.notesapp.user_interface.sections

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.user_interface.theme.NEUTRALS_900

@Composable
fun NoteVisualizingSection(note: Note, innerPadding: PaddingValues) {
    val noteTitleBoxScrollState = rememberScrollState()
    val noteBodyBoxScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 13.dp)
                .horizontalScroll(noteTitleBoxScrollState)
        ) {
            Text(
                text = note.title,
                color = NEUTRALS_900,
                fontSize = 40.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp,
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 16.dp)
                .fillMaxSize()
                .verticalScroll(noteBodyBoxScrollState)
        ) {
            Text(
                text = note.body,
                color = NEUTRALS_900,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            )
        }
    }
}