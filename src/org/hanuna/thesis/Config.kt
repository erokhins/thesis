package org.hanuna.thesis

public val CLIENTS_COUNT: Int = 100
public val PIECES_COUNT: Int = 1000

public val P: Double = 0.2

public val ITERATIONS: Int = Math.round(CLIENTS_COUNT*PIECES_COUNT* P).toInt()

public object ClientCapabilitiesFactory {

    fun bar(a: List<Int>) {
        a.joinToString()
    }
}

fun main(args: Array<String>) {
    val model = emptyModel(CLIENTS_COUNT, PIECES_COUNT)
    Model3.step(model, ITERATIONS)
    val amongInfo = model.piecesAmongInfo
    val min = amongInfo.among.min()!!
    println(min.toDouble() / CLIENTS_COUNT)
}