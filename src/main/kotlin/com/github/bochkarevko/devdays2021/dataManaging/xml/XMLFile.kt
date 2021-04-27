package com.github.bochkarevko.devdays2021.dataManaging.xml

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Duration
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@Serializable
class XMLFile(@XmlAttribute var name: String, @XmlAttribute var owner: String, @XmlAttribute @Contextual var duration: Duration)