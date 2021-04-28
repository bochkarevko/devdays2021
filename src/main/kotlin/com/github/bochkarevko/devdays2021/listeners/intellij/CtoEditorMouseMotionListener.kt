package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener

class CtoEditorMouseMotionListener : EditorMouseMotionListener {
    override fun mouseDragged(e: EditorMouseEvent) {
        super.mouseDragged(e)
        println("mouseDragged ${e.editor.document}")
    }
}