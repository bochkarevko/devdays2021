package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class MyDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        println("documentChanged ${event.document}")
        super.documentChanged(event)
    }
}