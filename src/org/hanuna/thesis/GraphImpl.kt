package org.hanuna.thesis

import java.util.ArrayList
import java.util.BitSet

class GraphImpl(override val clientsCount: Int, override val piecesCount: Int) : MutableGraph {
    val bits = BitSet(clientsCount * piecesCount)

    override fun set(client: Int, piece: Int, value: Boolean) {
        bits[edgeIndex(client, piece)] = value
    }

    override fun get(client: Int, piece: Int) = bits[edgeIndex(client, piece)]

    // 0..(clientCount - 1) * piecesCount + piecesCount - 1
    private fun edgeIndex(client: Int, piece: Int) = client * piecesCount + piece
}

class ModelImpl(currentGraph: MutableGraph) : MutableModel {
    override val currentGraph: Graph = currentGraph
    override var iteration: Long = 0
    override var time: Double = 0.toDouble()

    private val infoCollection = ArrayList<MutableModelInfo>()

    init {
        infoCollection.add(currentGraph)
    }

    override fun createEdge(client: Int, piece: Int) = infoCollection.forEach { it.set(client, piece, true) }

    override fun addModelInfo(infoCollector: MutableModelInfo) {
        infoCollection.add(infoCollector)
    }

    override fun <T : ModelInfo> getModelInfo(klass: Class<T>): T? {
        infoCollection.forEach {
            if (klass.isInstance(it)) return it as T
        }
        return null
    }
}

class PiecesAmongInfoImpl(piecesCount: Int): PiecesAmongInfo, MutableModelInfo {
    override val among = IntArray(piecesCount)

    override fun set(client: Int, piece: Int, value: Boolean) {
        if (value) among[piece]++
        else among[piece]--
    }
}