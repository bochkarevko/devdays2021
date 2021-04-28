package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.Disposable
import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener

class MyScrolledListener : VisibleAreaListener {

    override fun visibleAreaChanged(e: VisibleAreaEvent) {
        print("hello " + e.editor.document.toString())
        e.editor.scrollingModel.addVisibleAreaListener(this)
    }

}