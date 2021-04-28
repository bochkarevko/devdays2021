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
        val manager: XMLDataManager

        init {
            val newFileOwners = File("ownerFile.xml")
            newFileOwners.writeText("<root/>")
            val newFileMy = File("myOwnerFile.xml")
            newFileMy.writeText("<root/>")
            manager = XMLDataManager(newFileOwners.toPath(), newFileMy.toPath(), "Test")
        }

        fun sendAction(@NotNull fileName: Path, type: actionType) {
            if (lastFile == null) {
                lastFile = fileName
                startTime = getTime()
            }
            if (lastFile != fileName) {
                checkInputTime(fileName)
                lastFile = fileName
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
                val fileInfo = manager.getFileInfo(fileName)
                fileInfo.duration = fileInfo.duration.plusMillis(delta)
                startTime = getTime()
            } else {
                TODO()
            }
        }
    }
}