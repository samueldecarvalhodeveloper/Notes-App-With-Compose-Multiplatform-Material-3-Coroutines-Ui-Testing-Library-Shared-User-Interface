package org.example.notesapp.user_interface.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.notesapp.data.external_models.Note
import org.example.notesapp.user_interface.components.NoteItem

@Composable
fun ListOfNotesSection(listOfNotes: List<Note>, onNoteItemClick: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listOfNotes.size) { index ->
            NoteItem(
                note = listOfNotes[index],
                onClick = {
                    onNoteItemClick(listOfNotes[index].id)
                }
            )
        }
    }
}