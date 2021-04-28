package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener

class CtoVisibleAreaListener : VisibleAreaListener {
    override fun visibleAreaChanged(e: VisibleAreaEvent) {
        println("visibleAreaChanged ${e.editor.document}")
    }
}