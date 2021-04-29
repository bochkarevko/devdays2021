package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.io.File
import java.nio.file.Path
import javax.xml.bind.annotation.*

@XmlRootElement(name = "dir")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLPublicDirectory(
    @XmlAttribute val name: String? = null,
    @XmlTransient internal var parent: XMLPublicRootDirectory? = null,
    dirs: MutableList<XMLPublicDirectory> = mutableListOf(),
    files: MutableList<XMLPublicFile> = mutableListOf(),
) : XMLPublicRootDirectory(dirs, files) {
    override var path: Path?
        get() = File(parent!!.path!!.toFile(), name!!).toPath()
        set(value) {}

    override fun toString(): String {
        return "dir($name, dirs=$dir, files=$file)"
    }
}
