package com.github.bochkarevko.devdays2021.dataManaging.xml

import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow

object Utils {
    private const val perDayDecayCoefficient = 0.995

    fun decayDuration(duration: Duration, time: ZonedDateTime): Duration {
        val daysPassed = ChronoUnit.DAYS.between(time, ZonedDateTime.now())
        val thisDecay = perDayDecayCoefficient.pow(daysPassed.toDouble())
        return Duration.ofNanos((duration.toNanos() * thisDecay).toLong())
    }
}