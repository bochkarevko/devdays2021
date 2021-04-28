package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.SelectionEvent
import com.intellij.openapi.editor.event.SelectionListener

class MySelectionListener : SelectionListener {
    override fun selectionChanged(e: SelectionEvent) {
        super.selectionChanged(e)
        println("selectionChanged ${e.editor.document}")
    }
}