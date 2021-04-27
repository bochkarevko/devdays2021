package com.github.bochkarevko.devdays2021.services

import com.github.bochkarevko.devdays2021.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
