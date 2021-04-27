package com.github.bochkarevko.devdays2021.dataManaging

import java.nio.file.Path

interface DataManager {
    fun createFileInfo(path: Path): FileInfo
    fun getFileInfoByPath(path: Path): FileInfo
}