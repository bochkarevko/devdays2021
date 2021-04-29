package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import java.awt.Rectangle

class CtoVisibleAreaListener : VisibleAreaListener {
    private fun scrolled(old: Rectangle?, new: Rectangle?) = (old != null && new != null && old.location != new.location)

    override fun visibleAreaChanged(e: VisibleAreaEvent) {
        if (scrolled(e.oldRectangle, e.newRectangle)) {
            val a = FileDocumentManager.getInstance().getFile(e.editor.document)
            if (a != null) {
                MainClass.sendAction(a.toNioPath(), actionType.VISIBLE_AREA)
            }
        }
    }
}