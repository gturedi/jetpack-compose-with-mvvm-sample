package com.gturedi.github.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gturedi.github.theme.LoadingBg

@Composable
fun LoadingView() {
    Box(
        Modifier
            .fillMaxSize()
            //.background(Color.Gray),
            .background(LoadingBg),
        Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}