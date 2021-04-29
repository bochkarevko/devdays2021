package com.github.bochkarevko.devdays2021.listeners.intellij

import ClaimDialog
import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile

class CtoFileEditorManagerListener : FileEditorManagerListener {
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
//        MainClass.sendAction(file.toNioPath(), actionType.OPEN_FILE)
//        println("open" + file.toNioPath())
        super.fileOpened(source, file)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        MainClass.sendAction(file.toNioPath(), actionType.CLOSE_FILE)
        println("close")
        super.fileClosed(source, file)

    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        event.newFile?.let {
            MainClass.sendAction(it.toNioPath(), actionType.SWITCH_FILE)
        }
        event.oldFile?.let {
            val fileInfo = MainClass.manager?.getFileInfo(it.toNioPath())
            if (fileInfo != null && fileInfo.canOwn()) {
                ClaimDialog(fileInfo.path).show()
            }
        }
        super.selectionChanged(event)
    }
}

