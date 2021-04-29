package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.nio.file.Path
import javax.xml.bind.annotation.*

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
open class XMLPublicRootDirectory(
    @XmlElement val dir: MutableList<XMLPublicDirectory> = mutableListOf(),
    @XmlElement val file: MutableList<XMLPublicFile> = mutableListOf(),
) {
    @XmlTransient open var path: Path? = null

    override fun toString(): String {
        return "root(dirs=$dir, files=$file)"
    }
}
