package com.github.bochkarevko.devdays2021

import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLDataManager
import com.github.bochkarevko.devdays2021.utils.actionType
import org.jetbrains.annotations.NotNull
import java.io.File
import java.nio.file.Path

class MainClass {
    companion object {
        private const val BOUND_MIN: Long = 2 * 1000
        private const val BOUND_MAX: Long = 9 * 1000
        var startTime: Long = 0
        var lastFile: Path? = null
        var myFile: File? = null
        var generalFile : File? = null
        var projectPath : Path? = null
            set(value){
                field = value
                myFile = File(projectPath!!.toFile(), "myOwnerFile.xml" )
                //if (!myFile!!.isFile){
                myFile!!.writeText("<root/>")
                //}
                generalFile = File(projectPath!!.toFile(), "ownerFile.xml" )
                //if (!generalFile!!.isFile){
                generalFile!!.writeText("<root/>")
                //}
                manager = XMLDataManager(
                    projectPath!!,
                    generalFile!!.toPath(),
                    myFile!!.toPath(),
                    "TestOwner"
                )
            }
        var manager : XMLDataManager? = null

        fun sendAction(@NotNull fileName: Path, type: actionType) {
            if (lastFile == null) {
                lastFile = fileName
                startTime = getTime()
            }
            when(type){
                actionType.SWITCH_FILE -> {
                    checkTime()
                    saveData()
                    persistChanges()
                    lastFile = fileName
                    startTime = getTime()
                }
                else-> checkTime()
            }
        }

        private fun getTime(): Long {
            return System.currentTimeMillis()
        }

        private fun checkTime() {
            val delta = getTime() - startTime
            if (delta < BOUND_MAX) {
                return
            } else {
                startTime = getTime()
            }
        }

        private fun saveData(){
            val delta = getTime() - startTime
            val fileInfo = manager!!.getFileInfo(lastFile!!)
            fileInfo.duration = fileInfo.duration.plusMillis(delta)!!
        }

        fun persistChanges(){
            manager!!.persist()
        }
    }
}