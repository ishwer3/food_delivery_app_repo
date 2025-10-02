package com.example.fooddeliveryapp.data.local

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fooddeliveryapp.R

@Composable
fun sampleText(wordCount: Int): String {
    val fullText = stringResource(R.string.sample_text)
    return fullText.split(" ")
        .take(wordCount)
        .joinToString(" ")
}

@Composable
fun buttonText() = stringResource(R.string.button_text)