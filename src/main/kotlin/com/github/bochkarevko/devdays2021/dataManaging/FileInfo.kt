package com.github.bochkarevko.devdays2021.dataManaging

import com.github.bochkarevko.devdays2021.dataManaging.xml.Utils.decayDuration
import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLDataManager
import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLPrivateFile
import com.github.bochkarevko.devdays2021.dataManaging.xml.XMLPublicFile
import java.nio.file.Path
import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow

class FileInfo(
    val path: Path,
    private val manager: DataManager,
    internal var publicXmlFile: XMLPublicFile? = null,
    internal var privateXmlFile: XMLPrivateFile? = null
) {
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
            privateXmlFile = (manager as XMLDataManager).touch(manager.privateRootDirectory, path)
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