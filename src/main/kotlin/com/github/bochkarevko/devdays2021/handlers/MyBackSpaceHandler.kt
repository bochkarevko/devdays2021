package com.github.bochkarevko.devdays2021.handlers

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class MyBackSpaceHandler : BackspaceHandlerDelegate() {
    override fun beforeCharDeleted(p0: Char, file: PsiFile, editor: Editor) {
        //MainClass.sendAction(file.name, actionType.BACKSPACE)
    }

    override fun charDeleted(p0: Char, file: PsiFile, editor: Editor): Boolean {
        return true;
    }

}