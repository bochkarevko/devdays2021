package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.SelectionEvent
import com.intellij.openapi.editor.event.SelectionListener

class CtoSelectionListener : SelectionListener {
    override fun selectionChanged(e: SelectionEvent) {
        super.selectionChanged(e)
        println("selectionChanged ${e.editor.document}")
    }
}