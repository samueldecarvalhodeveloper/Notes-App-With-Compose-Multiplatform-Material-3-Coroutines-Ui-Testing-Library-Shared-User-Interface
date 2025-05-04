package org.example.notesapp.user_interface.sections

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.LOADING_SECTION_CONTENT_DESCRIPTION
import org.example.notesapp.user_interface.theme.PRIMARY_500

@Composable
fun LoadingSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = LOADING_SECTION_CONTENT_DESCRIPTION },
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        CircularProgressIndicator(color = PRIMARY_500)
    }
}