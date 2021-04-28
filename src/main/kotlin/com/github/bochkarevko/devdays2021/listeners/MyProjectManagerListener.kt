package com.github.bochkarevko.devdays2021.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.editor.Editor

internal class MyProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        //project.service<MyProjectService>()
        println("project opened??")
    }


    override fun projectClosed(project: Project) {
        super.projectClosed(project)
    }
}