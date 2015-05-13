package org.hanuna.thesis

import java.util.*

data class Config(
        val clientsCount: Int,
        val piecesCount: Int,
        val p: Double,
        val triesForModel: Int
) {
    val iterations = Math.round(clientsCount * piecesCount * p).toInt()
}

val TASKS = listOf(
        configs(10, 100, 0.1),
        configs(100, 100, 0.05), configs(100, 1000, 0.05),
        configs(500, 500, 0.05), configs(500, 5000, 0.05), configs(500, 10000, 0.05)
)

fun configs(clientsCount: Int,
            piecesCount: Int,
            deltaP: Double,
            triesForModel: Int = 50
): List<Config> {
    var p = deltaP
    val result = ArrayList<Config>()
    while (p < 1) {
        result.add(Config(clientsCount, piecesCount, p, triesForModel))
        p += deltaP
    }
    return result
}




val tab = "   "

fun modelIteration(model: ModelUpdater, config: Config) {
    try {
        val results = config.triesForModel.indices.map { getResult(model, config).toDouble() / config.clientsCount }
        val expectationAndVariety = expectationAndVariety(results)

        println("$tab ${model.javaClass.getSimpleName()} : ${expectationAndVariety.first} ± ${Math.sqrt(expectationAndVariety.second)}")
    } catch (e: Throwable) {
        println("$tab Exception due calculation ${model.javaClass.getSimpleName()}")
        e.printStackTrace()
    }

}

fun runForConfig(config: Config) {
    println(config)
    modelIteration(Model1, config)
    modelIteration(Model2, config)
    modelIteration(Model3, config)
    println()
}

fun printTasks() {
    TASKS.forEachIndexed { i, list ->
        println("TASK $i:")
        list.forEach { println("$tab $it") }
        println()
        println()
    }
}

fun main(args: Array<String>) {
    printTasks()
    TASKS.forEachIndexed { i, configs ->
        println("Start task $i")

        configs.forEach {  runForConfig(it) }

        println("Done task $i")
        println()
        println()
    }
}

private fun getResult(modelUpdater: ModelUpdater, config: Config): Int {
    val model = emptyModel(config.clientsCount, config.piecesCount)
    modelUpdater.step(model, config.iterations)
    val amongInfo = model.piecesAmongInfo
    return amongInfo.among.min()!!
}

