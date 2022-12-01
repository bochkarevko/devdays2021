package com.github.bochkarevko.devdays2021.listeners

import com.github.bochkarevko.devdays2021.listeners.intellij.*
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener

class CtoEditorFactoryListener : EditorFactoryListener {
    override fun editorCreated(event: EditorFactoryEvent) {
        super.editorCreated(event)
        val editor = event.editor
        editor.scrollingModel.addVisibleAreaListener(CtoVisibleAreaListener())
        editor.document.addDocumentListener(CtoDocumentListener())
        editor.selectionModel.addSelectionListener(CtoSelectionListener())
        editor.addEditorMouseMotionListener(CtoEditorMouseMotionListener())
        editor.addEditorMouseListener(CtoEditorMouseListener())
        editor.caretModel.addCaretListener(CtoCaretListener())
    }
}