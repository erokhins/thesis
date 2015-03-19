package org.hanuna.thesis


trait Graph {
    val clientsCount: Int
    val piecesCount: Int

    fun get(client: Int, piece: Int): Boolean
}

trait InfoCollector {
    fun set(client: Int, piece: Int, value: Boolean)
}

trait MutableGraph : Graph, InfoCollector

trait Model {
    val iteration: Long
    val time: Double

    val currentGraph: Graph
}

trait MutableModel : Model {
    override var iteration: Long
    override var time: Double

    fun createEdge(client: Int, value: Int)

    fun addInfoCollector(infoCollector: InfoCollector)
}
