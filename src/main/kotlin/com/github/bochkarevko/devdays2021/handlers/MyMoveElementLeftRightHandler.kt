package com.github.bochkarevko.devdays2021.handlers

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.codeInsight.editorActions.moveLeftRight.MoveElementLeftRightHandler
import com.intellij.psi.PsiElement

class MyMoveElementLeftRightHandler : MoveElementLeftRightHandler() {
    // Whe is this triggered???
    override fun getMovableSubElements(element: PsiElement): Array<PsiElement> {
        //MainClass.sendAction(name, actionType.MOVELEFTRIGHT)
        return arrayOf()
    }
}