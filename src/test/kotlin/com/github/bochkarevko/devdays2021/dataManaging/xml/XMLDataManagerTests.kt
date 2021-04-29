package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.dataManaging.FileInfo
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow

class XMLDataManagerTests {
    val projPath: Path
    val manager: XMLDataManager

    init {
        projPath = File("./src").toPath()
        manager = XMLDataManager(
            projPath,
            File(projPath.toFile(), "test/resources/public_config.xml" ).toPath(),
            File(projPath.toFile(), "test/resources/private_config.xml" ).toPath(),
            "petya229",
        )
    }


    @Test
    fun test() {
        manager.getFileInfo(File("src/foo/bar.txt").toPath())
        manager.getFileInfo(File("src/foo/bar.txt").toPath())
        manager.getFileInfo(File("src/foo/bar2.txt").toPath())
        manager.getFileInfo(File("src/foo/bar3.txt").toPath())
        manager.getFileInfo(File("src/foo2/bar.txt").toPath())
        manager.getFileInfo(File("src/foo3/bar.txt").toPath())
        manager.getFileInfo(File("src/foo3/bar.txt").toPath())
        manager.persist()
    }

    @Test
    fun testDecay() {
        val time = ZonedDateTime.now().minusMinutes(1)
        val duration = Duration.ofHours(1)
        val chrono = ChronoUnit.DAYS
        val daysPassed = chrono.between(time, ZonedDateTime.now())
        println(duration)
        println(daysPassed)
        println(0.995.pow(daysPassed.toDouble()))
        println(Duration.ofNanos(duration.toNanos() * 0.995.pow(daysPassed.toDouble()).toLong()))
    }
}