package com.github.bochkarevko.devdays2021.listeners

import com.github.bochkarevko.devdays2021.MainClass
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.editor.Editor
import java.io.File

internal class CtoProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {

        MainClass.projectPath = File(project.basePath).toPath()
        println("project opened??")
    }


    override fun projectClosed(project: Project) {
        super.projectClosed(project)
    }
}