package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.dataManaging.DataManager
import com.github.bochkarevko.devdays2021.dataManaging.FileInfo
import com.intellij.util.io.isFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller


class XMLDataManager(
    private var publicDocumentPath: Path,
    private var privateDocumentPath: Path,
    override val defaultOwner: String
) : DataManager {
    private val publicRootDirectory: XMLRootDirectory
    internal val privateRootDirectory: XMLRootDirectory
    internal val projectPath = File(".").toPath().normalize().toAbsolutePath()
    private val jaxbContext = JAXBContext.newInstance(XMLRootDirectory::class.java)
    private val marshaller = jaxbContext.createMarshaller()
    private val fileInfoMap = mutableMapOf<Path, FileInfo>() // if have PrivateFileInfo then PrivateFileInfo else PublicFileInfo

    init {
        if (!publicDocumentPath.isFile() || !privateDocumentPath.isFile()) {
            throw IllegalArgumentException("documentPath should lead to some file")
        }
        publicDocumentPath = publicDocumentPath.normalize()
        privateDocumentPath = privateDocumentPath.normalize()

        val unmarshaller = jaxbContext.createUnmarshaller()
        publicRootDirectory = unmarshaller.unmarshal(publicDocumentPath.toFile()) as XMLRootDirectory
        privateRootDirectory = unmarshaller.unmarshal(privateDocumentPath.toFile()) as XMLRootDirectory

        // set parents
        setParents(privateRootDirectory)
        setParents(publicRootDirectory)

        // merge them into fileInfoMap
        addAllPublicToMap(publicRootDirectory)
        addAllPrivateToMap(privateRootDirectory)

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

        // output to test JAXB
        println("=====================")
        marshaller.marshal(publicRootDirectory, System.out)
        marshaller.marshal(privateRootDirectory, System.out)
        println("=====================")
    }

    override fun getFileInfo(path: Path): FileInfo {
        val path = path.normalize()
        if (Files.exists(path) && !path.isFile()) {
            throw IllegalArgumentException("Is not a file")
        }
//        if (!path.toAbsolutePath().startsWith(projectPath)) {
//            throw IllegalArgumentException("File is not in our project directory")
//        }
        return if (!fileInfoMap.containsKey(path)) {
            createFileInfo(path)
        } else {
            fileInfoMap[path]!!
        }
    }

    private fun createFileInfo(path: Path): FileInfo {
        val publicFile = touch(publicRootDirectory, path)
        val privateFile = touch(privateRootDirectory, path)
        val file = FileInfo(path, this, publicFile, privateFile) // TODO: don't create new privateFile
        fileInfoMap[path] = file
        return file
    }

    override fun persist() {
        marshaller.marshal(publicRootDirectory, publicDocumentPath.toFile())
        marshaller.marshal(privateRootDirectory, privateDocumentPath.toFile())
        println("=====================")
        println(publicRootDirectory)
        println(privateRootDirectory)
        println("=====================")
        marshaller.marshal(publicRootDirectory, System.out)
        marshaller.marshal(privateRootDirectory, System.out)
        println("=====================")
    }

    private fun setParents(dir: XMLRootDirectory) {
        dir.file.forEach { f ->
            if (dir !is XMLDirectory) {
                f.parent = null
            } else {
                f.parent = dir
            }
        }
        dir.dir.forEach { d ->
            if (dir !is XMLDirectory) {
                d.parent = null
            } else {
                d.parent = dir
            }
            setParents(d)
        }
    }

    private fun addAllPublicToMap(dir: XMLRootDirectory) {
        dir.file.forEach { f -> fileInfoMap[f.path] = toPublicFileInfo(f) }
        dir.dir.forEach { d ->
            addAllPublicToMap(d)
        }
    }

    private fun addAllPrivateToMap(dir: XMLRootDirectory) {
        dir.file.forEach { f -> fileInfoMap[f.path] = toPrivateFileInfo(f) }
        dir.dir.forEach { d -> addAllPrivateToMap(d) }
    }

    private fun toPublicFileInfo(xmlFile: XMLFile): FileInfo {
        if (xmlFile.name == null || xmlFile.owner == null) {
            throw Exception("Problem in code: File name or owner is null")
        }
        return FileInfo(xmlFile.path, this, xmlFile, null)
    }

    private fun toPrivateFileInfo(xmlFile: XMLFile): FileInfo {
        if (xmlFile.name == null) {
            throw Exception("Problem in code: File name is null")
        }
        val publicFile = fileInfoMap[xmlFile.path]!!
        return FileInfo(xmlFile.path, this, publicFile.publicXmlFile, xmlFile)
    }

    internal fun touch(directory: XMLRootDirectory, path: Path): XMLFile {
        var currNode = directory
        val separated = path.getSeparated(projectPath)
        for (currDirName in separated.subList(0, separated.size - 1)) {
            val nextNode = currNode.dir.find { d -> d.name == currDirName }
            currNode = if (nextNode == null) {
                val parent = if (currNode !is XMLDirectory) null else currNode
                val newNode = XMLDirectory(currDirName, parent)
                currNode.dir.add(newNode)
                newNode
            } else {
                nextNode
            }
        }
        val fileName = separated[separated.size - 1]
        val file = currNode.file.find { f -> f.name == fileName }
        return if (file == null) {
            val newFileInfo = XMLFile(fileName, currNode, defaultOwner)
            currNode.file.add(newFileInfo)
            newFileInfo
        } else {
            file
        }
    }
}

internal fun Path.getSeparated(projectPath: Path): List<String> {
    return projectPath.toAbsolutePath().relativize(this.toAbsolutePath()).iterator().asSequence().toList().map { p -> p.toFile().name }
}