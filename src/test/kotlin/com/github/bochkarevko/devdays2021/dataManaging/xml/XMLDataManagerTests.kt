package com.github.bochkarevko.devdays2021.dataManaging.xml

import org.junit.Test
import java.io.File

class XMLDataManagerTests {
    @Test
    fun test() {
        val projPath = File("./src").toPath()
        val manager = XMLDataManager(
            projPath,
            File(projPath.toFile(), "test/resources/public_config.xml" ).toPath(),
            File(projPath.toFile(), "test/resources/private_config.xml" ).toPath(),
            "petya229",
        )
        manager.getFileInfo(File("src/foo/bar.txt").toPath())
        manager.getFileInfo(File("src/foo/bar.txt").toPath())
        manager.getFileInfo(File("src/foo/bar2.txt").toPath())
        manager.getFileInfo(File("src/foo/bar3.txt").toPath())
        manager.getFileInfo(File("src/foo2/bar.txt").toPath())
        manager.getFileInfo(File("src/foo3/bar.txt").toPath())
        manager.getFileInfo(File("src/foo3/bar.txt").toPath())
        manager.persist()
    }
}