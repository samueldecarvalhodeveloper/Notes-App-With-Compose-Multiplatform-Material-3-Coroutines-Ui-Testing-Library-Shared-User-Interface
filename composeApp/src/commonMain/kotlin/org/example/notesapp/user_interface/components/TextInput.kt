package org.example.notesapp.user_interface.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import kotlin.Int.Companion.MAX_VALUE

@Composable
fun TextInput(
    value: String,
    placeholder: String,
    textColor: Color,
    placeholderColor: Color,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit,
    singleLine: Boolean,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    var textState by rememberSaveable { mutableStateOf(value) }

    BasicTextField(
        value = textState,
        modifier = modifier,
        singleLine = singleLine,
        decorationBox = { innerTextField ->
            if (textState.isEmpty()) {
                Text(
                    text = placeholder,
                    color = placeholderColor,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    letterSpacing = letterSpacing,
                    maxLines = if (singleLine) 1 else MAX_VALUE
                )
            } else {
                innerTextField()
            }
        },
        textStyle = TextStyle(
            color = textColor,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
        ),
        onValueChange = {
            textState = it

            onValueChange(it)
        },
    )
}