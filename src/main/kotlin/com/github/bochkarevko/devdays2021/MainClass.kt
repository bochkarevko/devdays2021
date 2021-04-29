package com.github.bochkarevko.devdays2021

import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLDataManager
import com.github.bochkarevko.devdays2021.utils.actionType
import org.jetbrains.annotations.NotNull
import java.io.File
import java.nio.file.Path

class MainClass {
    companion object {
        private const val BOUND_MIN: Long = 2 * 1000
        private const val BOUND_MAX: Long = 120 * 1000
        var startTime: Long = 0
        var lastFile: Path? = null
        var projectPath : Path? = null
            set(value){
                field = value
                val my = File(projectPath!!.toFile(), "myOwnerFile.xml" )
                my.writeText("<root/>")
                val owner = File(projectPath!!.toFile(), "ownerFile.xml" )
                owner.writeText("<root/>")
                manager = XMLDataManager(
                    projectPath!!,
                    owner.toPath(),
                    my.toPath(),
                    "TestOwner"
                )
            }
        var manager : XMLDataManager? = null

        fun sendAction(@NotNull fileName: Path, type: actionType) {
            if (lastFile == null) {
                lastFile = fileName
                startTime = getTime()
            }
            if (lastFile != fileName) {
                checkInputTime(fileName)
                lastFile = fileName
            } else{
                checkInputTime(fileName)
            }
        }

        private fun getTime(): Long {
            return System.currentTimeMillis()
        }

        fun checkInputTime(fileName: Path) {
            val delta = getTime() - startTime
            if (delta < BOUND_MIN) {
                return
            }
            if (delta < BOUND_MAX) {
                val fileInfo = manager?.getFileInfo(fileName)
                println(fileName + delta)
                //File("test.txt").writeText(fileName.toString() + delta.toString())
                fileInfo!!.duration = fileInfo.duration.plusMillis(delta)!!
                manager!!.persist()
                startTime = getTime()
            } else {
                lastFile = null
            }
        }
    }
}
