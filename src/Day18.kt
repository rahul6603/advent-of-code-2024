fun main() {

    fun part1(input: List<List<Int>>, gridSize: Int, numBytes: Int): Int {
        val grid = List(gridSize) { MutableList(gridSize) { '.' } }
        val visited = List(gridSize) { MutableList(gridSize) { false } }
        for ((corruptRow, corruptCol) in input.subList(0, numBytes)) {
            grid[corruptRow][corruptCol] = '#'
        }
        val deque = ArrayDeque(listOf(0 to 0 to 0))
        while (deque.isNotEmpty()) {
            val size = deque.size
            repeat(size) {
                val (position, distance) = deque.removeFirst()
                val (row, col) = position
                if (row == gridSize - 1 && col == gridSize - 1) return distance
                for (direction in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
                    val (newRow, newCol) = row + direction.first to col + direction.second
                    if (newRow !in 0..<gridSize || newCol !in 0..<gridSize ||
                        visited[newRow][newCol] || grid[newRow][newCol] == '#'
                    ) continue
                    visited[newRow][newCol] = true
                    deque.addLast(newRow to newCol to distance + 1)
                }
            }
        }
        return -1
    }

    fun part2(input: List<List<Int>>, gridSize: Int): String {
        var (left, right) = 1025 to input.size
        while (left < right) {
            val mid = (left + right) / 2
            if (part1(input, gridSize, mid) == -1) right = mid
            else left = mid + 1
        }
        return input[left - 1].joinToString(",")
    }

    val input = readInput("Day18")
    println(part1(convertToIntListsByRow(input, ','), 71, 1024))
    println(part2(convertToIntListsByRow(input, ','), 71))
}