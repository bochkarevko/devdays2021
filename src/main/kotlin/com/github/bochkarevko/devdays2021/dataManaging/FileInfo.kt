package com.github.bochkarevko.devdays2021.dataManaging

import com.github.bochkarevko.devdays2021.dataManaging.xml.*
import com.github.bochkarevko.devdays2021.dataManaging.xml.Utils.decayDuration
import com.intellij.util.io.isDirectory
import com.intellij.util.io.isFile
import java.nio.file.Path
import java.time.Duration
import java.time.ZonedDateTime

class FileInfo(
    path: Path,
    private val manager: XMLDataManager,
    internal var publicXmlFile: XMLPublicFile? = null,
    internal var privateXmlFile: XMLPrivateFile? = null
) {
    var path = path
        set(value) {
            val oldPath = path
            val newPath = value.normalize().toAbsolutePath()
            if (newPath == path) {
                throw Exception("newPath is the same")
            }
            if (!newPath.startsWith(manager.projectPath)) {
                throw Exception("newPath is outside of our projectPath")
            }
//            if (!newPath.isFile()) {
//                throw Exception("newPath is not a directory, or not exist")
//            }

            val relativePath = path.normalize().toAbsolutePath().relativize(newPath.normalize().toAbsolutePath())
            val separated = relativePath.iterator().asSequence().toList()
            var currPath = path
            // iterate over directories in relativePath
            for (pathPart in separated.subList(0, separated.size - 1)) {
                println("pathPart=$pathPart")
                currPath = currPath.resolve(pathPart)
                val privateParent = privateXmlFile?.parent
                val publicParent = publicXmlFile?.parent
                if (pathPart.toFile().name == "..") {
                    // move file to grandParent dir

                    if (privateParent != null) {
                        if (privateParent is XMLPrivateDirectory) {
                            val privateGrandParent = privateParent.parent!!
                            privateParent.file.remove(privateXmlFile)
                            privateXmlFile!!.parent = privateGrandParent
                            if (privateParent.file.isEmpty() && privateParent.dir.isEmpty()) {
                                privateGrandParent.dir.remove(privateParent)
                            }
                        } else {
                            throw Exception("Wrong newPath. Probably is not in our projectDir")
                        }
                    }

                    if (publicParent != null) {
                        if (publicParent is XMLPublicDirectory) {
                            val publicGrandParent = publicParent.parent!!
                            publicParent.file.remove(publicXmlFile)
                            publicXmlFile!!.parent = publicGrandParent
                            if (publicParent.file.isEmpty() && publicParent.dir.isEmpty()) {
                                publicGrandParent.dir.remove(publicParent)
                            }
                        } else {
                            throw Exception("Wrong newPath. Probably is not in our projectDir")
                        }
                    }
                } else {
                    // move file to next dir

                    val newPrivateDir = manager.touchDir(manager.privateRootDirectory, currPath)
                    val newPublicDir = manager.touchDir(manager.publicRootDirectory, currPath)
                    if (privateParent != null) {
                        privateXmlFile!!.parent!!.file.remove(privateXmlFile)
                        privateXmlFile!!.parent!!.dir.add(newPrivateDir as XMLPrivateDirectory)
                        newPrivateDir.file.add(privateXmlFile!!)
                        privateXmlFile!!.parent = newPrivateDir
                    }
                    if (publicParent != null) {
                        publicXmlFile!!.parent!!.file.remove(publicXmlFile)
                        publicXmlFile!!.parent!!.dir.add(newPublicDir as XMLPublicDirectory)
                        newPublicDir.file.add(publicXmlFile!!)
                        publicXmlFile!!.parent = newPublicDir
                    }
                }
            }

            // rename file
            if (privateXmlFile != null) {
                privateXmlFile!!.name = newPath.fileName.toFile().name
            }
            if (publicXmlFile != null) {
                publicXmlFile!!.name = newPath.fileName.toFile().name
            }
            manager.fileInfoMap.remove(oldPath)
            manager.fileInfoMap[newPath] = this
            field = newPath
        }

    var canClaim: Boolean
        get() = when (privateXmlFile) {
            null -> false
            else -> privateXmlFile!!.canClaim
        }
        set(value) = when (privateXmlFile) {
            null -> throw Exception("You can't set canClaim, when you didn't set duration before")
            else -> privateXmlFile!!.canClaim = value
        }

    val duration: Duration
        get() = when (privateXmlFile) {
            null -> Duration.ZERO
            else -> privateXmlFile!!.duration
        }

    fun addExtraDuration(extra: Duration) {
        if (privateXmlFile == null) {
            privateXmlFile = manager.touch(manager.privateRootDirectory, path)
        }
        val newDuration = decayDuration(duration, privateXmlFile!!.lastTouched).plus(extra)
        val now = ZonedDateTime.now()
        privateXmlFile!!.duration = newDuration
        privateXmlFile!!.lastTouched = now
        if (isOwner()) {
            publicXmlFile!!.duration = newDuration
            publicXmlFile!!.lastTouched = now
        }
    }

    fun isOwner(): Boolean {
        return privateXmlFile != null && publicXmlFile != null && // check this just in case
                manager.defaultOwner == publicXmlFile!!.owner
    }

    fun getPublicOwner(): String? {
        return publicXmlFile?.owner
    }

    fun canOwn(): Boolean {
        return privateXmlFile != null && privateXmlFile!!.canClaim &&
                (publicXmlFile == null ||
                        decayDuration(publicXmlFile!!.duration, publicXmlFile!!.lastTouched) <
                        decayDuration(duration, privateXmlFile!!.lastTouched))
    }

    fun tryClaimingOwnership() {
        if (canOwn()) {
            if (publicXmlFile == null) {
                publicXmlFile = (manager as XMLDataManager).touch(manager.publicRootDirectory, path)
            }
            publicXmlFile!!.duration = duration
            publicXmlFile!!.owner = manager.defaultOwner
            publicXmlFile!!.lastTouched = privateXmlFile!!.lastTouched
        }
    }
}
