package com.github.bochkarevko.devdays2021.listeners.intellij

import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener
import java.awt.Rectangle

class CtoVisibleAreaListener : VisibleAreaListener {
    private fun scrolled(old: Rectangle, new: Rectangle) = (old.location != new.location)

    override fun visibleAreaChanged(e: VisibleAreaEvent) {
        if (scrolled(e.oldRectangle, e.newRectangle)) {
            println("visibleAreaChanged ${e.editor.document}")
        }
//        else {
//            println("dimensions change")
//        }
    }
}