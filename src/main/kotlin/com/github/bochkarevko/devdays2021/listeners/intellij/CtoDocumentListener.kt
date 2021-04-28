package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class CtoDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        val a = FileDocumentManager.getInstance().getFile(event.document)
        if (a != null) {
            MainClass.sendAction(a.toNioPath(), actionType.CARET)
        }
        super.documentChanged(event)
    }
}