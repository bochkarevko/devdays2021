package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener

class CtoCaretListener : CaretListener {
    override fun caretAdded(event: CaretEvent) {
        super.caretAdded(event)
        println("caredAdded ${event.editor.document}")
    }

    override fun caretPositionChanged(event: CaretEvent) {
        super.caretPositionChanged(event)
        println("caretPositionChanged ${event.editor.document}")
    }
}