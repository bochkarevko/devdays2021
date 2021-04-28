package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener

class MyEditorMouseMotionListener : EditorMouseMotionListener {
    override fun mouseDragged(e: EditorMouseEvent) {
        super.mouseDragged(e)
        println("mouseDragged ${e.editor.document}")
    }
}