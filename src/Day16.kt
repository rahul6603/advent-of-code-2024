import java.util.PriorityQueue

data class State(val position: Pair<Int, Int>, val direction: Pair<Int, Int>, val score: Int)

fun main() {

    fun part1and2(input: List<MutableList<Char>>): Pair<Int, Int> {
        val initialDirection = 0 to 1
        val initialPosition = findInitialPosition(input, 'S')
        val finalPosition = findInitialPosition(input, 'E')
        val pq = PriorityQueue<State>(compareBy { it.score })
        pq.add(State(initialPosition, initialDirection, 0))
        val distance = mutableMapOf(initialPosition to (0 to 1) to 0)

        while (pq.isNotEmpty()) {
            val (position, currentDirection, score) = pq.remove()
            val (row, col) = position
            for (direction in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
                if (direction == currentDirection && input[row][col] == '#') continue
                val newPosition =
                    if (direction == currentDirection) row + direction.first to col + direction.second else position
                val newScore = score + if (currentDirection == direction) 1 else 1000
                val currentScore = distance.getOrDefault(newPosition to direction, Int.MAX_VALUE)
                if (newScore <= currentScore) {
                    distance[newPosition to direction] = newScore
                    pq.add(State(newPosition, direction, newScore))
                }
            }
        }

        val finalPositionDistance =
            listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0).mapNotNull { dir -> distance[finalPosition to dir] }.min()

        val visited = List(input.size) { MutableList(input[0].size) { false } }
        val path = List(input.size) { MutableList(input[0].size) { false } }

        fun dfs(row: Int, col: Int, currentDirection: Pair<Int, Int>, currentDistance: Int) {
            if (path[row][col] || input[row][col] == '#' || currentDistance > distance[row to col to currentDirection]!!) return
            path[row][col] = true
            if (row == finalPosition.first && col == finalPosition.second && currentDistance == finalPositionDistance) {
                for (i in path.indices) {
                    for (j in path[0].indices) {
                        if (path[i][j]) visited[i][j] = true
                    }
                }
            }
            for (direction in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
                val (newRow, newCol) = row + direction.first to col + direction.second
                dfs(newRow, newCol, direction, currentDistance + if (direction == currentDirection) 1 else 1001)
            }
            path[row][col] = false
        }
        dfs(initialPosition.first, initialPosition.second, initialDirection, 0)
        return finalPositionDistance to visited.sumOf { row -> row.count { it } }
    }

    val input = readInput("Day16").map { it.toMutableList() }
    val part1and2 = part1and2(input)
    println(part1and2.first)
    println(part1and2.second)
}