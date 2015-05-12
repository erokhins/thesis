package org.hanuna.thesis

import java.util.ArrayList
import java.util.BitSet

class GraphImpl(override val clientsCount: Int, override val piecesCount: Int) : MutableGraph {
    val bits = BitSet(clientsCount * piecesCount)
    var updateFun: ((client: Int, piece: Int, value: Boolean)-> Unit)? = null

    override fun set(client: Int, piece: Int, value: Boolean) {
        updateFun?.invoke(client, piece, value)
        bits[edgeIndex(client, piece)] = value
    }

    override fun get(client: Int, piece: Int) = bits[edgeIndex(client, piece)]

    // 0..(clientCount - 1) * piecesCount + piecesCount - 1
    private fun edgeIndex(client: Int, piece: Int) = client * piecesCount + piece
}

class ModelImpl(currentGraph: GraphImpl) : MutableModel {
    override val piecesAmongInfo: PiecesAmongInfoImpl = PiecesAmongInfoImpl(currentGraph.piecesCount)

    override val currentGraph: MutableGraph = currentGraph
    override var iteration: Long = 0
    override var time: Double = 0.toDouble()

    init {
        currentGraph.updateFun = { client, count, piece ->
            piecesAmongInfo.set(client, count, piece)

        }
    }

}

class PiecesAmongInfoImpl(piecesCount: Int): PiecesAmongInfo, MutableModelInfo {
    override val among = IntArray(piecesCount)

    override fun set(client: Int, piece: Int, value: Boolean) {
        if (value) among[piece]++
        else among[piece]--
    }
}

fun emptyModel(clients: Int, pieces: Int) : MutableModel {
    val model = ModelImpl(GraphImpl(clients, pieces))
    return model
}