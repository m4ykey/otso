package com.m4ykey.core.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun LoadingMaxSize(modifier : Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val progressBar = createRef()
        CircularProgressIndicator(
            modifier = modifier.constrainAs(progressBar) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun LoadingMaxWidth(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val progressBar = createRef()
        CircularProgressIndicator(
            modifier = modifier.constrainAs(progressBar) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        )
    }
}