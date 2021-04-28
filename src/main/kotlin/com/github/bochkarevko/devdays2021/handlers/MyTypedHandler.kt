package com.github.bochkarevko.devdays2021.handlers

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

internal class MyTypedHandler : TypedHandlerDelegate() {
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        //MainClass.sendAction(file.name, actionType.TYPEDCODE)
        return Result.STOP
    }
}