package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class CtoCaretListener : CaretListener {
    override fun caretAdded(event: CaretEvent) {
        super.caretAdded(event)
    }

    override fun caretPositionChanged(event: CaretEvent) {
        super.caretPositionChanged(event)
        val a = FileDocumentManager.getInstance().getFile(event.editor.document)
        if (a != null) {
            MainClass.sendAction(a.toNioPath(), actionType.CARET)
        }
    }
}