package org.hanuna.thesis

trait ModelInfo

trait Graph: ModelInfo {
    val clientsCount: Int
    val piecesCount: Int

    fun get(client: Int, piece: Int): Boolean
}

trait MutableModelInfo {
    fun set(client: Int, piece: Int, value: Boolean)
}

trait MutableGraph : Graph, MutableModelInfo

trait PiecesAmongInfo: ModelInfo {
    val among: IntArray
    val minAmong: Int
    val minIndexes: Set<Int>
    val minIndex: Int
}

trait Model {
    val iteration: Long
    val time: Double

    val currentGraph: Graph
}

trait MutableModel : Model {
    override var iteration: Long
    override var time: Double

    override val currentGraph: MutableGraph

    val piecesAmongInfo: PiecesAmongInfo
}
