package org.example.notesapp.user_interface.sections

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.example.notesapp.user_interface.theme.NEUTRALS_300

@Composable
fun NoNotesSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        Text(
            text = "No notes",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = NEUTRALS_300
        )
    }
}