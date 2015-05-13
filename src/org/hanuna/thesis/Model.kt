package org.hanuna.thesis


trait ModelUpdater {
    fun step(model: MutableModel)
}

fun ModelUpdater.step(model: MutableModel, times: Int) = (0..times - 1).forEach {
//    if (it mod 10 == 0) println(it)
    step(model)
}

fun MutableGraph.tryAdd(client: Int, piece: Int): Boolean {
    if (!get(client, piece)) {
        set(client, piece, true)
        return true
    }
    return false
}

object Model3_first : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        val amongInfo = model.piecesAmongInfo
        notMore100(graph.clientsCount) {

            val client = random(0..graph.clientsCount - 1)

            // optimization
            if (graph.tryAdd(client, amongInfo.minIndex)) return

            var minEdge = graph.clientsCount
            var pieceIndex = -1
            for (piece in 0..graph.piecesCount - 1) {
                if (!graph[client, piece]) {
                    if (minEdge > amongInfo.among[piece]) {
                        minEdge = amongInfo.among[piece]
                        pieceIndex = piece
                    }
                }
            }
            if (pieceIndex != -1) {
                graph[client, pieceIndex] = true
                return
            }
        }
    }
}

object Model3_fix : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        val amongInfo = model.piecesAmongInfo
        notMore100(graph.clientsCount) {

            val client = random(0..graph.clientsCount - 1)
            val minIndexes = amongInfo.minIndexes
            notMore100(minIndexes.size(), false) {
                val someIndex = minIndexes.getRandom()!!
                if (graph.tryAdd(client, someIndex)) return
            }
        }
    }
}

object Model1 : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        notMore100(graph.clientsCount * graph.piecesCount) {
            val client = random(0..graph.clientsCount - 1)
            val piece = random(0..graph.piecesCount - 1)
            if (!graph[client, piece]) {
                graph[client, piece] = true
                return
            }
        }
    }

}

inline fun notMore100(counts: Int = 100, throwE: Boolean = true, run: () -> Unit) {
    for (i in 0..counts * 10) {
        run()
    }
    if (throwE)
        throw IllegalStateException()
}

object Model2 : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        notMore100(graph.clientsCount) {
            val client = random(graph.clientsCount.indices)
            val countEdges = graph.piecesCount.indices.sumBy { if (graph[client, it]) 0 else 1 }
            var edgeIndex = random(countEdges.indices)
            for (piece in graph.piecesCount.indices) {
                if (!graph[client, piece]) {
                    if (edgeIndex == 0) {
                        graph[client, piece] = true
                        return
                    }
                    edgeIndex--
                }
            }
        }
    }
}

object Model2_2 : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        notMore100(graph.clientsCount) {
            val client = random(graph.clientsCount.indices)
            for (i in 0..graph.piecesCount * 10) {
                val piece = random(graph.piecesCount.indices)
                if (!graph[client, piece]) {
                    graph[client, piece] = true
                    return
                }
            }
        }
    }
}

//object ChosePieceWithPriority : ModelUpdater {
//
//    override fun step(model: MutableModel) {
//        val amongInfo = model.getModelInfo(javaClass<PiecesAmongInfo>())!!
//        val piece = amongInfo.chosePiece()
////        val client = model.currentGraph.choseClient(piece)
//
////        model.createEdge(client, piece)
//    }
//
//    private fun PiecesAmongInfo.chosePiece(): Int {
//        val commonProbability = among.sumByDouble { if (it > 0) 1.0 / it else 0.0 }
//        val chose = random(0..commonProbability)
//
//        var sum = 0.0
//        for (i in 0..among.size() - 1) {
//            val value = among[i]
//            if (value > 0) sum += 1 / value
//
//            if (sum >= chose) return i
//        }
//
//        return among.size() - 1
//    }
////
////    private fun Graph.choseClient(piece: Int): Int {
////
////    }
//}