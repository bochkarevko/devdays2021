package com.github.bochkarevko.devdays2021.actions

import com.github.bochkarevko.devdays2021.MainClass
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileDocumentManager

class ReadyToClaimAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val document = e.getData(CommonDataKeys.EDITOR)?.document
        val file = document?.let { FileDocumentManager.getInstance().getFile(it) }
        if (file != null) {
            MainClass.manager?.getFileInfo(file.toNioPath())?.canClaim = true
        }
        println("action performed")
    }
}

