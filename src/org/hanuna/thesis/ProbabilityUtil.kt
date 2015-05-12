package org.hanuna.thesis

val random01: Double
    get() = Math.random()

fun random(min: Double = 0.0, max: Double = 1.0) = random01 * (max - min) + min

fun random(range: DoubleRange) = random(range.start, range.end)