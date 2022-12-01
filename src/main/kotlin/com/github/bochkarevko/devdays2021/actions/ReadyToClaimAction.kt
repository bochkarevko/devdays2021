package com.github.bochkarevko.devdays2021.actions

import com.github.bochkarevko.devdays2021.MainClass
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileDocumentManager

class ReadyToClaimAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val path = e.getFilePath()
        if (path != null) {
            MainClass.manager?.getFileInfo(path)?.canClaim = true
        }
    }
}

