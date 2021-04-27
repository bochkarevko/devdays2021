package com.github.bochkarevko.devdays2021.dataManaging.xml

import kotlinx.serialization.Serializable
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@Serializable
class XMLDirectory(@XmlElement val dirs: List<XMLDirectory>, @XmlAttribute var name: String)