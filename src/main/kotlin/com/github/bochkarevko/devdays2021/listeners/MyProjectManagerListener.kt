package com.github.bochkarevko.devdays2021.listeners

import com.github.bochkarevko.devdays2021.services.MyProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
    }

    override fun projectClosed(project: Project) {
        super.projectClosed(project)
        // save xml
    }
}
