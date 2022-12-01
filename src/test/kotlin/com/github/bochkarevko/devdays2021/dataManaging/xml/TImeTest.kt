package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.MainClass
import com.github.bochkarevko.devdays2021.utils.actionType
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class TImeTest {
    @Test
    fun simpleTest() {
        var result = MainClass.forTest(File("Foo").toPath(), actionType.DOCUMENT_CHANGED)
        assertEquals(result, "Foo 0")
        Thread.sleep(1_000)
        result = MainClass.forTest(File("Foo").toPath(), actionType.DOCUMENT_CHANGED)
        assertEquals(result, "Foo 1")
        Thread.sleep(7_000)
        result = MainClass.forTest(File("Foo").toPath(), actionType.CARET)
        assertEquals(result, "Foo 8")
        Thread.sleep(9_001)
        result = MainClass.forTest(File("Foo").toPath(), actionType.CARET)
        assertEquals(result, "Foo 8")
        Thread.sleep(1_000)
        result = MainClass.forTest(File("Boo").toPath(), actionType.SWITCH_FILE)
        assertEquals(result, "Boo 0")
        Thread.sleep(2_000)
        result = MainClass.forTest(File("Boo").toPath(), actionType.SELECTION)
        assertEquals(result, "Boo 1")

    }


}