package com.bigflowertiger.compopsebingwallpaper.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.bigflowertiger.compopsebingwallpaper.config.BASE_URL

@Composable
fun BigViewScreen(imgUrl: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(model = "$BASE_URL/$imgUrl", contentDescription = null)
    }
}