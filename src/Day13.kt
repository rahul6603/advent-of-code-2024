fun main() {

    operator fun <T> List<T>.component6() = this[5]

    fun part1and2(input: List<Long>, part1: Boolean): Long {
        var numTokens = 0L
        repeat(input.size / 6) { idx ->
            val (aX, aY, bX, bY, prizeX, prizeY) = listOf(
                input[idx * 6],
                input[idx * 6 + 1],
                input[idx * 6 + 2],
                input[idx * 6 + 3],
                input[idx * 6 + 4] + if (part1) 0 else 10000000000000,
                input[idx * 6 + 5] + if (part1) 0 else 10000000000000
            )
            val matrix1 = listOf(
                listOf(aX, bX),
                listOf(aY, bY)
            )
            val matrix2 = listOf(
                listOf(prizeX),
                listOf(prizeY)
            )
            val det = matrix1[0][0] * matrix1[1][1] - matrix1[0][1] * matrix1[1][0]
            if (det != 0L) { // Intersecting lines (Unique solution)
                val adj = listOf(listOf(matrix1[1][1], -matrix1[0][1]), listOf(-matrix1[1][0], matrix1[0][0]))
                val buttonAPressTimesDet = adj[0][0] * matrix2[0][0] + adj[0][1] * matrix2[1][0]
                val buttonBPressTimesDet = adj[1][0] * matrix2[0][0] + adj[1][1] * matrix2[1][0]
                if (buttonAPressTimesDet % det == 0L && buttonBPressTimesDet % det == 0L) {
                    numTokens += 3 * buttonAPressTimesDet / det + buttonBPressTimesDet / det
                }
            } else if (aX * bY == aY * bX && aX * prizeY == aY * prizeX) { // Coincident lines (Choosing minimum tokens)
                var minimumTokens = Long.MAX_VALUE
                for (buttonAPress in 0..prizeX / aX) {
                    if ((prizeX - buttonAPress * aX) % bX == 0L) {
                        val tokens = 3 * buttonAPress + (prizeX - buttonAPress * aX) / bX
                        minimumTokens = if (tokens < minimumTokens) tokens else minimumTokens
                    }
                }
                numTokens += minimumTokens
            }
        }
        return numTokens
    }

    val input = readInputAsOneLine("Day13").split('\n', ',', '+', '=').mapNotNull { it.toLongOrNull() }
    println(part1and2(input, true))
    println(part1and2(input, false))
}