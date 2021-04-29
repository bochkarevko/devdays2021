package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class CtoFileEditorManagerListener : FileEditorManagerListener {
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        MainClass.sendAction(file.toNioPath(), actionType.OPEN_FILE)
        super.fileOpened(source, file)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        MainClass.sendAction(file.toNioPath(), actionType.OPEN_FILE)
        super.fileClosed(source, file)

    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        event.newFile?.let { MainClass.sendAction(it.toNioPath(), actionType.SWITCH_FILE) }
        super.selectionChanged(event)
        println("selectionChanged")
    }
}

