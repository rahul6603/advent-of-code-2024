fun main() {

    fun traverseGrid(
        row: Int,
        col: Int,
        previousValue: Int,
        input: List<List<Int>>,
        visited: List<MutableList<Boolean>>,
        part1: Boolean
    ): Int {
        if (row < 0 || row >= input.size || col < 0 || col >= input[0].size ||
            visited[row][col] || input[row][col] != previousValue + 1
        ) {
            return 0
        }
        visited[row][col] = true
        if (input[row][col] == 9) {
            visited[row][col] = part1
            return 1
        }
        var score = 0
        for (direction in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
            score += traverseGrid(
                row + direction.first, col + direction.second,
                previousValue + 1, input, visited, part1
            )
        }
        visited[row][col] = part1
        return score
    }

    fun part1and2(input: List<List<Int>>, part1: Boolean): Int {
        var sum = 0
        for (row in input.indices) {
            for (col in input[0].indices) {
                if (input[row][col] == 0) {
                    val visited = List(input.size) { MutableList(input[0].size) { false } }
                    sum += traverseGrid(row, col, -1, input, visited, part1)
                }
            }
        }
        return sum
    }

    val input = readInput("Day10").map { it.split("").filter { it != "" }.map { it.toInt() } }
    println(part1and2(input, true))
    println(part1and2(input, false))
}
