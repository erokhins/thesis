package org.hanuna.thesis


trait ModelUpdater {
    fun step(model: MutableModel)
}

fun ModelUpdater.step(model: MutableModel, times: Int) = (0..times - 1).forEach {
//    if (it mod 10 == 0) println(it)
    step(model)
}

object Model3 : ModelUpdater {
    override fun step(model: MutableModel) {
        notMore100 {
            val graph = model.currentGraph
            val amongInfo = model.piecesAmongInfo

            val client = random(0..graph.clientsCount - 1)
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
                return@notMore100 true
            }
            false
        }
    }
}

object Model1 : ModelUpdater {
    override fun step(model: MutableModel) {
        val graph = model.currentGraph
        for (i in 0..1000) {
            val client = random(0..graph.clientsCount - 1)
            val piece = random(0..graph.piecesCount - 1)
            if (!graph[client, piece]) {
                graph[client, piece] = true
                return
            }
        }
        throw IllegalStateException()
    }

}

fun notMore100(run: () -> Boolean) {
    for (i in 0..100) {
        if (run()) return
    }
    throw IllegalStateException()
}

object Model2 : ModelUpdater {
    override fun step(model: MutableModel) {
        notMore100 {
            val graph = model.currentGraph
            val client = random(graph.clientsCount.indices)
            val countEdges = graph.piecesCount.indices.sumBy { if (graph[client, it]) 0 else 1 }
            var edgeIndex = random(countEdges.indices)
            for (piece in graph.piecesCount.indices) {
                if (!graph[client, piece]) {
                    if (edgeIndex == 0) {
                        graph[client, piece] = true
                        return@notMore100 true
                    }
                    edgeIndex--
                }
            }
            return@notMore100 false
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