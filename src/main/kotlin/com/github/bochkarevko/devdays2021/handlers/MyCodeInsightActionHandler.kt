package com.github.bochkarevko.devdays2021.handlers

import com.intellij.codeInsight.CodeInsightActionHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class MyCodeInsightActionHandler : CodeInsightActionHandler {
    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        println("Smth happened to ${file.name}???")
    }
}