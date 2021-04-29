package com.github.bochkarevko.devdays2021.listeners

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.listeners.intellij.CtoFileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import java.io.File

internal class CtoProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {

        MainClass.projectPath = File(project.basePath).toPath()
        println("project opened??")
        project.messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, CtoFileEditorManagerListener())

    }


    override fun projectClosed(project: Project) {
        super.projectClosed(project)
        MainClass.manager?.persist()
    }
}