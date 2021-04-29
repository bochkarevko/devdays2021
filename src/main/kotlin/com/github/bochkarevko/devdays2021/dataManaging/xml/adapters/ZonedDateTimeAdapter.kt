package com.github.bochkarevko.devdays2021.dataManaging.xml.adapters

import java.time.Duration
import java.time.ZonedDateTime
import javax.xml.bind.annotation.adapters.XmlAdapter

class ZonedDateTimeAdapter : XmlAdapter<String?, ZonedDateTime>() {
    override fun unmarshal(v: String?): ZonedDateTime? {
        return ZonedDateTime.parse(v)
    }

    override fun marshal(v: ZonedDateTime): String? {
        return v.toString()
    }
}