package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener

class MyEditorFactoryListener : EditorFactoryListener {
    override fun editorCreated(event: EditorFactoryEvent) {
        super.editorCreated(event)
        println("Editor created, adding listeners")
        val editor = event.editor
        editor.scrollingModel.addVisibleAreaListener(MyVisibleAreaListener())
        editor.document.addDocumentListener(MyDocumentListener())
        editor.selectionModel.addSelectionListener(MySelectionListener())
        editor.addEditorMouseMotionListener(MyEditorMouseMotionListener())
        editor.addEditorMouseListener(MyEditorMouseListener())
    }
}