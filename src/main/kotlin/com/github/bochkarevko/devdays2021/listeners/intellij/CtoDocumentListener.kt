package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class CtoDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        println("documentChanged ${event.document}")
        super.documentChanged(event)
    }
}