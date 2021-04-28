package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class CtoEditorMouseListener : EditorMouseListener {
    override fun mouseClicked(event: EditorMouseEvent) {
        super.mouseClicked(event)
        println("mouseClicked ${event.editor.document}")
    }

    override fun mouseEntered(event: EditorMouseEvent) {
        super.mouseEntered(event)
        // println("mouseEntered ${event.editor.document}")
    }

    override fun mousePressed(event: EditorMouseEvent) {
        super.mousePressed(event)
        val a = FileDocumentManager.getInstance().getFile(event.editor.document)
        println(a)
        if (a != null) {
            MainClass.sendAction(a.toNioPath(), actionType.CARET)
        }
    }

    override fun mouseReleased(event: EditorMouseEvent) {
        super.mouseReleased(event)
        println("mouseReleased ${event.editor.document}")
    }
}