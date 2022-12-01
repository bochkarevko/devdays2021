package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class CtoEditorMouseMotionListener : EditorMouseMotionListener {
    override fun mouseDragged(e: EditorMouseEvent) {
        super.mouseDragged(e)
        val a = FileDocumentManager.getInstance().getFile(e.editor.document)
        if (a != null) {
            MainClass.sendAction(a.toNioPath(), actionType.MOUSE_DRAGGED)
        }
    }
}