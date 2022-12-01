package com.github.bochkarevko.devdays2021

import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLDataManager
import com.github.bochkarevko.devdays2021.utils.actionType
import org.jetbrains.annotations.NotNull
import java.io.File
import java.nio.file.Path
import java.time.Duration

class MainClass {
    companion object {
        private const val BOUND_MAX: Long = 9 * 1000
        private const val COLD_TIME_ALPHA = 0.5

        var startTime: Long = 0
        var lastHotTime : Long = 0

        private var currentDelta: Long = 0

        private var lastFile: Path? = null

        var isHot : Boolean = false

        var myFile: File? = null

        var generalFile : File? = null

        var projectPath : Path? = null
            set(value){
                val name = System.getProperty("user.name")
                field = value
                myFile = File(projectPath!!.toFile(), "myOwnerFile.xml" )
                if (!myFile!!.isFile){
                    myFile!!.writeText("<root/>")
                }
                generalFile = File(projectPath!!.toFile(), "ownerFile.xml" )
                if (!generalFile!!.isFile){
                    generalFile!!.writeText("<root/>")
                }
                manager = XMLDataManager(
                    projectPath!!,
                    generalFile!!.toPath(),
                    myFile!!.toPath(),
                    name
                )
            }

        var manager : XMLDataManager? = null

        fun sendAction(@NotNull fileName: Path, type: actionType) {
            if (lastFile == null) {
                lastFile = fileName
                startTime = getTime()
            } else {
                when (type) {
                    actionType.SWITCH_FILE -> {
                        checkTime()
                        saveData()
                        lastFile = fileName
                        startTime = getTime()
                        currentDelta = 0
                    }
                    actionType.DOCUMENT_CHANGED -> {
                        isHot = true
                        lastHotTime = getTime()
                        checkTime()
                    }
                    else -> {
                        isHot = false
                        checkTime()
                    }
                }
            }
        }

        private fun getTime(): Long {
            return System.currentTimeMillis()
        }

        private fun checkTime() {
            val delta = getTime() - startTime
            if (delta < BOUND_MAX) {
                currentDelta += if (isHot){
                    delta
                } else {
                    if (getTime() - lastHotTime < BOUND_MAX){
                        delta
                    } else {
                        (delta * COLD_TIME_ALPHA).toLong()
                    }
                }
            }
            startTime = getTime()
        }

        fun saveData(){
            val fileInfo = manager!!.getFileInfo(lastFile!!)
            val duration = Duration.ofMillis(currentDelta)
            fileInfo.addExtraDuration(duration)
        }

        fun persistChanges(){
            manager!!.persist()
        }

        fun forTest(@NotNull fileName: Path, type: actionType) : String{
            lastFile = fileName
            when (type) {
                actionType.SWITCH_FILE -> {
                    checkTime()
                    startTime = getTime()
                    currentDelta = 0
                }
                actionType.DOCUMENT_CHANGED -> {
                    isHot = true
                    lastHotTime = getTime()
                    checkTime()
                }
                else -> {
                    isHot = false
                    checkTime()
                }
            }
            return lastFile.toString()+ " " + ((currentDelta/1000).toLong()).toString()
        }
    }
}