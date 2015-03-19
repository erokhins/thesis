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

class ModelImpl(override val currentGraph: MutableGraph) : MutableModel {
    override var iteration: Long = 0
    override var time: Double = 0.toDouble()

    private val infoCollection = ArrayList<InfoCollector>()

    override fun createEdge(client: Int, value: Int) = infoCollection.forEach { it.set(client, value, true) }

    override fun addInfoCollector(infoCollector: InfoCollector) {
        infoCollection.add(infoCollector)
    }
}

class PiecesCountInfo(piecesCount: Int): InfoCollector {
    val amoung = IntArray(piecesCount)

    override fun set(client: Int, piece: Int, value: Boolean) {
        if (value) amoung[piece]++
        else amoung[piece]--
    }

}