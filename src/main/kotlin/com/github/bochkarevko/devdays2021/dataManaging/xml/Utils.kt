package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow

object Utils {
    private const val COEF = 0.995

    fun decayDuration(duration: Duration, time: ZonedDateTime): Duration {
        val chrono = ChronoUnit.DAYS
        val daysPassed = chrono.between(time, ZonedDateTime.now())
        return Duration.ofNanos(duration.toNanos() * COEF.pow(daysPassed.toDouble()).toLong())
    }
}