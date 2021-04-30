package com.github.bochkarevko.devdays2021.listeners.intellij

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import com.intellij.psi.PsiTreeChangeEvent
import com.intellij.psi.PsiTreeChangeListener
import java.io.File

class FileRefListener : PsiTreeChangeListener{
    override fun beforeChildAddition(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun beforeChildRemoval(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun beforeChildReplacement(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun beforeChildMovement(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun beforeChildrenChange(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun beforePropertyChange(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun childAdded(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun childRemoved(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun childReplaced(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun childrenChanged(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun childMoved(event: PsiTreeChangeEvent) {
//        TODO("Not yet implemented")
    }

    override fun propertyChanged(event: PsiTreeChangeEvent) {
        println(event.propertyName)
        println(event.oldValue)
        println(event.newValue)
        var file = event.file
        if (file == null) file = event.element.containingFile
        println(file)
        println(file?.virtualFile)
        println(file?.virtualFile?.canonicalPath)

        if (file == null || file.virtualFile == null || file.virtualFile.canonicalPath == null) return
        val newPath = File(file.virtualFile!!.canonicalPath!!).toPath()
        val oldPath = File(newPath.parent.toFile(), event.oldValue as String).toPath()
        MainClass.sendAction(oldPath, actionType.RENAME_FILE, newPath)
    }
}