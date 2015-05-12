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
        while (true) {
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
                return
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