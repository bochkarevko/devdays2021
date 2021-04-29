package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.io.File
import java.nio.file.Path
import javax.xml.bind.annotation.*

@XmlRootElement(name = "dir")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLPrivateDirectory(
    @XmlAttribute val name: String? = null,
    @XmlTransient internal var parent: XMLPrivateRootDirectory? = null,
    dirs: MutableList<XMLPrivateDirectory> = mutableListOf(),
    files: MutableList<XMLPrivateFile> = mutableListOf(),
) : XMLPrivateRootDirectory(dirs, files) {
    override var path: Path?
        get() = File(parent!!.path!!.toFile(), name!!).toPath()
        set(value) {}

    override fun toString(): String {
        return "dir($name, dirs=$dir, files=$file)"
    }
}
