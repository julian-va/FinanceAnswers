package jva.cloud.financeanswers

import androidx.compose.ui.window.ComposeUIViewController
import jva.cloud.financeanswers.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }