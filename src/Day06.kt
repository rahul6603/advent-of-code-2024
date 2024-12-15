fun main() {

    /*
        ^   Initial position
        .   Empty space
        #   Unvisited obstacle
        0   Obstacle visited moving up
        1   Obstacle visited moving right
        2   Obstacle visited moving down
        3   Obstacle visited moving left
    */

    val directionList = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1) // Up, Right, Down, Left

    tailrec fun traverseGrid(
        input: List<MutableList<Char>>,
        currentPosition: Pair<Int, Int>,
        currentDirectionIdx: Int
    ): Boolean {
        val (currentRow, currentColumn) = currentPosition
        input[currentRow][currentColumn] = 'X'
        if (currentRow == 0 || currentColumn == 0 || currentRow == input.size - 1 || currentColumn == input[0].size - 1) {
            return false
        }
        val currentDirection = directionList[currentDirectionIdx]
        val (nextRow, nextColumn) = Pair(currentRow + currentDirection.first, currentColumn + currentDirection.second)
        if (input[nextRow][nextColumn] == '#' || input[nextRow][nextColumn] in listOf('0', '1', '2', '3')) {
            if (input[nextRow][nextColumn] == currentDirectionIdx.digitToChar()) {
                return true
            }
            if (input[nextRow][nextColumn] == '#') {
                input[nextRow][nextColumn] = currentDirectionIdx.digitToChar()
            }
            val nextDirectionIdx = (currentDirectionIdx + 1) % 4
            return traverseGrid(input, Pair(currentRow, currentColumn), nextDirectionIdx)
        }
        return traverseGrid(input, Pair(nextRow, nextColumn), currentDirectionIdx)
    }

    fun isValidPosition(input: List<MutableList<Char>>, row: Int, col: Int): Boolean {
        var trialInput = input.map { it.toMutableList() }
        trialInput[row][col] = '#'
        val currentPosition = findInitialPosition(trialInput, '^')
        val currentDirectionIdx = 0
        return traverseGrid(trialInput, currentPosition, currentDirectionIdx)
    }

    fun part1(input: List<MutableList<Char>>): Int {
        var input = input
        val currentPosition = findInitialPosition(input, '^')
        val currentDirectionIdx = 0
        traverseGrid(input, currentPosition, currentDirectionIdx)
        return input.sumOf { it.count { char -> char == 'X' } }
    }

    fun part2(input: List<MutableList<Char>>): Int {
        var input = input
        val inputWithX = input.map { it.toMutableList() }
        part1(inputWithX)
        var numPositions = 0
        for (row in input.indices) {
            for (col in input[0].indices) {
                // Only check for positions visited in part 1
                if (inputWithX[row][col] == 'X' && input[row][col] != '^' && isValidPosition(input, row, col)) {
                    numPositions++
                }
            }
        }
        return numPositions
    }

    val inputPart1 = readInput("Day06").map { it.toMutableList() }
    println(part1(inputPart1))
    val inputPart2 = readInput("Day06").map { it.toMutableList() }
    println(part2(inputPart2))
}