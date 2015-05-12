package org.hanuna.thesis

val random01: Double
    get() = Math.random()

fun random(min: Double = 0.0, max: Double = 1.0) = random01 * (max - min) + min

fun random(range: DoubleRange) = random(range.start, range.end)

fun random(range: IntRange): Int = Math.round(random(range.start - 0.4999, range.end + 0.4999)).toInt()
