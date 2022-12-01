package com.github.bochkarevko.devdays2021.dataManaging.xml

import com.github.bochkarevko.devdays2021.dataManaging.xml.adapters.DurationAdapter
import com.github.bochkarevko.devdays2021.dataManaging.xml.adapters.ZonedDateTimeAdapter
import java.io.File
import java.nio.file.Path
import java.time.Duration
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name="file")
@XmlAccessorType(XmlAccessType.FIELD)
// TODO: there should be different XMLFiles for public and private xml files
class XMLPublicFile(
    @XmlAttribute val name: String? = null,
    @XmlTransient internal var parent: XMLPublicRootDirectory? = null,
    @XmlAttribute var owner: String? = null,
    @XmlAttribute @field:XmlJavaTypeAdapter(
        type = Duration::class,
        value = DurationAdapter::class
    ) var duration: Duration = Duration.ZERO,
    @XmlAttribute @field:XmlJavaTypeAdapter(
        type = ZonedDateTime::class,
        value = ZonedDateTimeAdapter::class
    ) var lastTouched: ZonedDateTime = ZonedDateTime.now(),
) {
    val path: Path
        get() = File(parent!!.path!!.toFile(), name!!).toPath()

    override fun toString(): String {
        return "file($name',$owner',$duration)"
    }
}