package com.github.bochkarevko.devdays2021.dataManaging.xml

import org.junit.Test
import java.io.File

class XMLDataManagerTests {
    @Test
    fun test() {
        val manager = XMLDataManager(
            File("src/test/resources/public_config.xml" ).toPath(),
            File("src/test/resources/private_config.xml" ).toPath(),
            "petya229"
        )
        manager.getFileInfo(File("foo/bar.txt").toPath())
        manager.getFileInfo(File("foo/bar.txt").toPath())
        manager.getFileInfo(File("foo/bar2.txt").toPath())
        manager.getFileInfo(File("foo/bar3.txt").toPath())
        manager.getFileInfo(File("foo2/bar.txt").toPath())
        manager.getFileInfo(File("foo3/bar.txt").toPath())
        manager.getFileInfo(File("foo3/bar.txt").toPath())
        manager.persist()
    }
}