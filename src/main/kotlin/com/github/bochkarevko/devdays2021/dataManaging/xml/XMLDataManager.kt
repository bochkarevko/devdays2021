package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.dataManaging.DataManager
import com.github.bochkarevko.devdays2021.dataManaging.FileInfo
import com.intellij.util.io.isFile
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller


class XMLDataManager(
    internal var projectPath: Path,
    private var publicDocumentPath: Path,
    private var privateDocumentPath: Path,
    override val defaultOwner: String,
) : DataManager {
    internal val publicRootDirectory: XMLPublicRootDirectory
    internal val privateRootDirectory: XMLPrivateRootDirectory
    private val jaxbPublicContext = JAXBContext.newInstance(XMLPublicRootDirectory::class.java)
    private val publicMarshaller = jaxbPublicContext.createMarshaller()
    private val jaxbPrivateContext = JAXBContext.newInstance(XMLPrivateRootDirectory::class.java)
    private val privateMarshaller = jaxbPrivateContext.createMarshaller()
    private val fileInfoMap =
        mutableMapOf<Path, FileInfo>() // if have PrivateFileInfo then PrivateFileInfo else PublicFileInfo

    init {
        if (!publicDocumentPath.isFile() || !privateDocumentPath.isFile()) {
            throw IllegalArgumentException("documentPath should lead to some file")
        }
        projectPath = projectPath.normalize().toAbsolutePath()
        publicDocumentPath = publicDocumentPath.normalize().toAbsolutePath()
        privateDocumentPath = privateDocumentPath.normalize().toAbsolutePath()

        publicRootDirectory =
            jaxbPublicContext.createUnmarshaller().unmarshal(publicDocumentPath.toFile()) as XMLPublicRootDirectory
        publicRootDirectory.path = projectPath
        privateRootDirectory =
            jaxbPrivateContext.createUnmarshaller().unmarshal(privateDocumentPath.toFile()) as XMLPrivateRootDirectory
        privateRootDirectory.path = projectPath

        // set parents
        setParents(privateRootDirectory)
        setParents(publicRootDirectory)

        // merge them into fileInfoMap
        addAllToMap(publicRootDirectory)
        addAllToMap(privateRootDirectory)

        publicMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        privateMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

        // output to test JAXB
//        println("=====================")
//        publicMarshaller.marshal(publicRootDirectory, System.out)
//        privateMarshaller.marshal(privateRootDirectory, System.out)
//        println("=====================")
    }

    override fun getFileInfo(path: Path): FileInfo {
        val path = path.normalize()
        if (Files.exists(path) && !path.isFile()) {
            throw IllegalArgumentException("Is not a file")
        }
        if (!path.toAbsolutePath().startsWith(projectPath)) {
            throw IllegalArgumentException(
                "File is not in our project directory, proj_path=$projectPath, file_path=${path.toAbsolutePath()}"
            )
        }
        return if (!fileInfoMap.containsKey(path)) {
            createFileInfo(path)
        } else {
            fileInfoMap[path]!!
        }
    }

    private fun createFileInfo(path: Path): FileInfo {
        val publicFile = getIfPresent(publicRootDirectory, path)
        val privateFile = getIfPresent(privateRootDirectory, path)
        val file = FileInfo(path, this, publicFile, privateFile)
        fileInfoMap[path] = file
        return file
    }

    override fun persist() {
        publicMarshaller.marshal(publicRootDirectory, publicDocumentPath.toFile())
        privateMarshaller.marshal(privateRootDirectory, privateDocumentPath.toFile())
//        println("=====================")
        println("public=$publicRootDirectory")
        println("private=$privateRootDirectory")
//        println("=====================")
//        publicMarshaller.marshal(publicRootDirectory, System.out)
//        privateMarshaller.marshal(privateRootDirectory, System.out)
//        println("=====================")
    }

    private fun setParents(dir: XMLPublicRootDirectory) {
        dir.file.forEach { f -> f.parent = dir }
        dir.dir.forEach { d ->
            d.parent = dir
            setParents(d)
        }
    }

    private fun setParents(dir: XMLPrivateRootDirectory) {
        dir.file.forEach { f -> f.parent = dir }
        dir.dir.forEach { d ->
            d.parent = dir
            setParents(d)
        }
    }

    private fun addAllToMap(dir: XMLPublicRootDirectory) {
        dir.file.forEach { f -> fileInfoMap[f.path] = toFileInfo(f) }
        dir.dir.forEach { d -> addAllToMap(d) }
    }

    private fun addAllToMap(dir: XMLPrivateRootDirectory) {
        dir.file.forEach { f -> fileInfoMap[f.path] = toFileInfo(f) }
        dir.dir.forEach { d -> addAllToMap(d) }
    }

    private fun toFileInfo(xmlFile: XMLPublicFile): FileInfo {
        if (xmlFile.name == null || xmlFile.owner == null) {
            throw Exception("Problem in code: File name or owner is null")
        }
        return FileInfo(xmlFile.path, this, xmlFile, null)
    }

    private fun toFileInfo(xmlFile: XMLPrivateFile): FileInfo {
        if (xmlFile.name == null) {
            throw Exception("Problem in code: File name is null")
        }
        val publicFileInfo = fileInfoMap[xmlFile.path]
        return FileInfo(xmlFile.path, this, publicFileInfo?.publicXmlFile, xmlFile)
    }

    internal fun touch(directory: XMLPublicRootDirectory, path: Path): XMLPublicFile {
        var currNode = directory
        val separated = path.getSeparated(projectPath)
        for (currDirName in separated.subList(0, separated.size - 1)) {
            val nextNode = currNode.dir.find { d -> d.name == currDirName }
            currNode = if (nextNode == null) {
                val parent = if (currNode !is XMLPublicDirectory) null else currNode
                val newNode = XMLPublicDirectory(currDirName, parent)
                currNode.dir.add(newNode)
                newNode
            } else {
                nextNode
            }
        }
        val fileName = separated[separated.size - 1]
        val file = currNode.file.find { f -> f.name == fileName }
        return if (file == null) {
            val newFileInfo = XMLPublicFile(fileName, currNode, defaultOwner)
            currNode.file.add(newFileInfo)
            newFileInfo
        } else {
            file
        }
    }

    internal fun touch(directory: XMLPrivateRootDirectory, path: Path): XMLPrivateFile {
        var currNode = directory
        val separated = path.getSeparated(projectPath)
        for (currDirName in separated.subList(0, separated.size - 1)) {
            val nextNode = currNode.dir.find { d -> d.name == currDirName }
            currNode = if (nextNode == null) {
                val parent = if (currNode !is XMLPrivateDirectory) null else currNode
                val newNode = XMLPrivateDirectory(currDirName, parent)
                currNode.dir.add(newNode)
                newNode
            } else {
                nextNode
            }
        }
        val fileName = separated[separated.size - 1]
        val file = currNode.file.find { f -> f.name == fileName }
        return if (file == null) {
            val newFileInfo = XMLPrivateFile(fileName, currNode)
            currNode.file.add(newFileInfo)
            newFileInfo
        } else {
            file
        }
    }

    internal fun getIfPresent(directory: XMLPublicRootDirectory, path: Path): XMLPublicFile? {
        var currNode = directory
        val separated = path.getSeparated(projectPath)
        for (currDirName in separated.subList(0, separated.size - 1)) {
            val nextNode = currNode.dir.find { d -> d.name == currDirName } ?: return null
            currNode = nextNode
        }
        val fileName = separated[separated.size - 1]
        return currNode.file.find { f -> f.name == fileName }
    }

    internal fun getIfPresent(directory: XMLPrivateRootDirectory, path: Path): XMLPrivateFile? {
        var currNode = directory
        val separated = path.getSeparated(projectPath)
        for (currDirName in separated.subList(0, separated.size - 1)) {
            val nextNode = currNode.dir.find { d -> d.name == currDirName } ?: return null
            currNode = nextNode
        }
        val fileName = separated[separated.size - 1]
        return currNode.file.find { f -> f.name == fileName }
    }
}

internal fun Path.getSeparated(projectPath: Path): List<String> {
    return projectPath.toAbsolutePath().relativize(this.toAbsolutePath()).iterator().asSequence().toList()
        .map { p -> p.toFile().name }
}