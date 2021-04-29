package com.github.bochkarevko.devdays2021.dataManaging

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
    internal val publicXmlFile: XMLPublicFile,
    private var privateXmlFile: XMLPrivateFile?
) {
    private val COEF = 0.995
    var canClaim: Boolean
        get() = if (privateXmlFile == null) false else privateXmlFile!!.canClaim
        set(value) {
            if (privateXmlFile == null) {
                throw Exception("You can't set canClaim, when you didn't set duration before")
            }
            privateXmlFile!!.canClaim = value
        }

    val duration: Duration
        get() {
            if (privateXmlFile == null) {
                return Duration.ZERO
            }
            return privateXmlFile!!.duration
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
            publicXmlFile.duration = newDuration
            publicXmlFile.lastTouched = now
        }
    }

    fun isOwner(): Boolean {
        return privateXmlFile != null && // check this just in case
                manager.defaultOwner == publicXmlFile.owner
    }

    fun canOwn(): Boolean {
        return privateXmlFile != null && privateXmlFile!!.canClaim &&
                decayDuration(publicXmlFile.duration,publicXmlFile.lastTouched) <
                decayDuration(duration, privateXmlFile!!.lastTouched)
    }

    internal fun decayDuration(duration: Duration, time: ZonedDateTime): Duration {
        val chrono = ChronoUnit.DAYS
        val daysPassed = chrono.between(time, ZonedDateTime.now())
        return Duration.ofNanos(duration.toNanos() * COEF.pow(daysPassed.toDouble()).toLong())
    }

    fun tryClaimingOwnership() {
        if (canOwn()) {
            publicXmlFile.duration = duration
            publicXmlFile.owner = manager.defaultOwner
            publicXmlFile.lastTouched = privateXmlFile!!.lastTouched
        }
    }
}