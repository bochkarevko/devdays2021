package com.github.bochkarevko.devdays2021.actions

import com.github.bochkarevko.devdays2021.MainClass
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class UpdateOwnershipTime : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        MainClass.manager?.persist()
    }
}