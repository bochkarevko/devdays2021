package com.github.bochkarevko.devdays2021.dataManaging

import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLDataManager
import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLFile
import java.nio.file.Path
import java.time.Duration

class FileInfo(
    val path: Path,
    private val manager: DataManager,
    internal val publicXmlFile: XMLFile,
    private var privateXmlFile: XMLFile?
) {
    var duration: Duration
        get() {
            if (privateXmlFile == null) {
                return Duration.ZERO
            }
            return privateXmlFile!!.duration
        }
        set(value) {
            if (privateXmlFile == null) {
                privateXmlFile = (manager as XMLDataManager).touch(manager.privateRootDirectory, path)
            }
            privateXmlFile!!.duration = value
            if (isOwner()) {
                publicXmlFile.duration = value
            }
        }

    fun isOwner(): Boolean {
        return privateXmlFile != null && manager.defaultOwner == publicXmlFile.owner
    }

    fun canOwn(): Boolean {
        return duration > publicXmlFile.duration
    }

    fun tryClaimingOwnership() {
        if (canOwn()) {
            publicXmlFile.owner = manager.defaultOwner
        }
    }
}