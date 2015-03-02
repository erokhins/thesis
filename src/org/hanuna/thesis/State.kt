package org.hanuna.thesis


trait Graph {
    val clientsCount: Int
    val piecesCount: Int

    fun get(client: Int, piece: Int): Boolean
}

trait MutableGraph : Graph {

    fun set(client: Int, piece: Int, value: Boolean)
}


trait Model {
    val iteration: Long
    val time: Double

    val currentGraph: Graph
    val uploadClientsCapability: List<Double>
}

trait MutableModel : Model {
    override var iteration: Long
    override var time: Double


    override val currentGraph: MutableGraph
}