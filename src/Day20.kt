fun main() {

    fun part1and2(input: List<MutableList<Char>>, cheatTime: Int): Int {
        val initialPosition = findInitialPosition(input, 'S')
        val distances = List(input.size) { MutableList(input[0].size) { Int.MAX_VALUE } }
        val trackNodes = mutableListOf<Pair<Int, Int>>()
        val deque = ArrayDeque(listOf(Triple(initialPosition, 0 to 0, 0)))
        while (deque.isNotEmpty()) {
            val size = deque.size
            repeat(size) {
                val (position, currentDirection, distance) = deque.removeFirst()
                val (row, col) = position
                distances[row][col] = distance
                trackNodes.add(row to col)
                for (direction in listOf(
                    0 to 1,
                    0 to -1,
                    1 to 0,
                    -1 to 0
                ).filter { it != -currentDirection.first to -currentDirection.second }) {
                    val (newRow, newCol) = row + direction.first to col + direction.second
                    if (input[newRow][newCol] == '#') continue
                    deque.addLast(Triple(newRow to newCol, direction, distance + 1))
                }
            }
        }
        var ans = 0
        for (i in trackNodes.indices) {
            val (firstRow, firstCol) = trackNodes[i]
            for (j in i..<trackNodes.size) {
                val (secondRow, secondCol) = trackNodes[j]
                val pathDistance = distances[secondRow][secondCol] - distances[firstRow][firstCol]
                val manhattanDistance = findManhattanDistance(trackNodes[i], trackNodes[j])
                if (manhattanDistance <= cheatTime && pathDistance - manhattanDistance >= 100) ans++
            }
        }
        return ans
    }

    val input = readInput("Day20").map { it.toMutableList() }
    println(part1and2(input, 2))
    println(part1and2(input, 20))
}