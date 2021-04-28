package com.github.bochkarevko.devdays2021.dataManaging

import java.nio.file.Path

/**
 * Interface for persistent work with
 */
interface DataManager {
    val defaultOwner: String

    /**
     * TODO:
     * Creates new FileInfo, if path leads to existed file in this project
     *
     * @return
     * PrivateFileInfo - if did work with this file
     * PublicFileInfo - otherwise
     */
    fun getFileInfo(path: Path): FileInfo

    /**
     * Persist all info to the specified storage
     */
    fun persist()
}