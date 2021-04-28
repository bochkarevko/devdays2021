package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener

class MyVisibleAreaListener : VisibleAreaListener {
    override fun visibleAreaChanged(e: VisibleAreaEvent) {
        println("visibleAreaChanged ${e.editor.document}")
    }
}