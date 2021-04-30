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
class XMLPrivateFile(
    @XmlAttribute var name: String? = null,
    @XmlTransient internal var parent: XMLPrivateRootDirectory? = null,
    @XmlAttribute var canClaim: Boolean = true,
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
        return "file($name',$canClaim',$duration, $lastTouched)"
    }
}