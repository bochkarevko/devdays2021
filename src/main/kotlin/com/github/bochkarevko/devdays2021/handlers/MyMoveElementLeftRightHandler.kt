package com.github.bochkarevko.devdays2021.handlers

import com.intellij.codeInsight.editorActions.moveLeftRight.MoveElementLeftRightHandler
import com.intellij.psi.PsiElement

class MyMoveElementLeftRightHandler : MoveElementLeftRightHandler() {
    // Whe is this triggered???
    override fun getMovableSubElements(element: PsiElement): Array<PsiElement> {
        val name = element.containingFile
        println("Hello, $name! (move l/r)")
        return arrayOf()
    }
}