package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.io.File
import java.nio.file.Path
import javax.xml.bind.annotation.*

@XmlRootElement(name = "dir")
@XmlAccessorType(XmlAccessType.FIELD)
class XMLDirectory(
    @XmlAttribute val name: String? = null,
    @XmlTransient internal var parent: XMLDirectory? = null,
    dirs: MutableList<XMLDirectory> = mutableListOf(),
    files: MutableList<XMLFile> = mutableListOf(),
) : XMLRootDirectory(dirs, files) {
    val path: Path
        get() {
            if (parent == null || parent !is XMLDirectory) {
                return File(File("."), name!!).toPath()
            }
            return File(parent!!.path.toFile(), name!!).toPath()
        }

    override fun toString(): String {
        return "dir($name, dirs=$dir, files=$file)"
    }
}
