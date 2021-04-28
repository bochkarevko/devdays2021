package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.SelectionEvent
import com.intellij.openapi.editor.event.SelectionListener
import com.intellij.openapi.fileEditor.FileDocumentManager

class CtoSelectionListener : SelectionListener {
    override fun selectionChanged(e: SelectionEvent) {
        super.selectionChanged(e)
        val a = FileDocumentManager.getInstance().getFile(e.editor.document)
        if (a != null) {
            MainClass.sendAction(a.toNioPath(), actionType.SELECTION)
        }
    }
}