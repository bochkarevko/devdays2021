package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseListener

class MyEditorMouseListener : EditorMouseListener {
    override fun mouseClicked(event: EditorMouseEvent) {
        super.mouseClicked(event)
        println("mouseClicked ${event.editor.document}")
    }

    override fun mouseEntered(event: EditorMouseEvent) {
        super.mouseEntered(event)
        println("mouseEntered ${event.editor.document}")
    }

    override fun mousePressed(event: EditorMouseEvent) {
        super.mousePressed(event)
        println("mousePressed ${event.editor.document}")
    }

    override fun mouseReleased(event: EditorMouseEvent) {
        super.mouseReleased(event)
        println("mouseReleased ${event.editor.document}")
    }
}