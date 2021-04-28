package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.time.Duration
import javax.xml.bind.annotation.adapters.XmlAdapter

class DurationAdapter : XmlAdapter<String?, Duration>() {
    override fun unmarshal(v: String?): Duration? {
        return Duration.parse(v)
    }

    override fun marshal(v: Duration): String? {
        return v.toString()
    }
}