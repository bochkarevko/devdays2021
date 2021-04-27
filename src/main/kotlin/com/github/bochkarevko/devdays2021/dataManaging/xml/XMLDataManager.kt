package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.dataManaging.DataManager
import com.github.bochkarevko.devdays2021.dataManaging.FileInfo
import com.intellij.util.withNotNullBackup
import java.io.*
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.parsers.*
import javax.xml.transform.dom.DOMSource
import javax.xml.validation.SchemaFactory


class XMLDataManager(private var documentPath: Path, private var schemaPath: Path? = null) : DataManager {
    private val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(documentPath.toFile())
    private val rootDirectory: XMLDirectory
    private val projectPath = File(".").toPath()

    init {
        documentPath = documentPath.normalize()
        // we can validate xml schema of our document with the specified schema
        if (schemaPath != null) {
            schemaPath = schemaPath!!.normalize()
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)
                .newSchema(schemaPath!!.toFile())
                .newValidator()
                .validate(DOMSource(document))
        }

        val jaxbContext = JAXBContext.newInstance(XMLDirectory::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        rootDirectory = unmarshaller.unmarshal(documentPath.toFile()) as XMLDirectory

        // output to test JAXB
        val marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
//        marshaller.marshal(rootDirectory, documentPath.toFile())
        marshaller.marshal(rootDirectory, System.out)
    }

    override fun getFileInfoByPath(path: Path): FileInfo {
        var currNode = rootDirectory
        for (currDirName in path.getParents()) {
            currNode = currNode.dirs.find { d -> d.name == currDirName }.withNotNullBackup {
                throw IllegalArgumentException("No such file in the structure. Create it first. name=${path.relativize(projectPath)}")
            }
        }

        TODO("get file and convert to FileInfo")
    }

    override fun createFileInfo(path: Path): FileInfo {
        TODO("Not yet implemented")
    }

    private fun Path.getParents(): List<String> {
        val parents = mutableListOf<String>()
        for (p in this.relativize(projectPath).iterator()) {
            parents.add(0, p.toString())
        }
        return parents
    }
}