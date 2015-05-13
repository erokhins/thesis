package org.hanuna.thesis

public val CLIENTS_COUNT: Int = 100
public val PIECES_COUNT: Int = 1000

public val P: Double = 0.4

public val ITERATIONS: Int = Math.round(CLIENTS_COUNT*PIECES_COUNT* P).toInt()

public object ClientCapabilitiesFactory {

    fun bar(a: List<Int>) {
        a.joinToString()
    }
}

fun modelIteration(model: ModelUpdater, counts: Int = 50) {
    val results = (0..counts).map { getResult(model).toDouble() / CLIENTS_COUNT }
    val expectationAndVariety = expectationAndVariety(results)

    println("${model.javaClass.getSimpleName()} : ${expectationAndVariety.first} ± ${Math.sqrt(expectationAndVariety.second)}")

}

fun main(args: Array<String>) {
    modelIteration(Model1)
    modelIteration(Model2)
    modelIteration(Model3)
}

private fun getResult(modelUpdater: ModelUpdater): Int {
    val model = emptyModel(CLIENTS_COUNT, PIECES_COUNT)
    modelUpdater.step(model, ITERATIONS)
    val amongInfo = model.piecesAmongInfo
    return amongInfo.among.min()!!
}