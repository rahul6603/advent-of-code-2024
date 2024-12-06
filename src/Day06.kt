fun main() {

    /*
        ^   Initial position
        .   Empty space
        #   Unvisited obstacle
        U   Obstacle visited moving up
        R   Obstacle visited moving right
        D   Obstacle visited moving down
        L   Obstacle visited moving left
     */

    fun findInitialPosition(input: List<MutableList<Char>>): Pair<Int, Int> {
        for (row in input.indices) {
            for (col in input[0].indices) {
                if (input[row][col] == '^') {
                    return Pair(row, col)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun findNextPosition(input: List<MutableList<Char>>, currentPosition: Pair<Int, Int>): Pair<Int, Int> {
        val (currentRow, currentColumn) = currentPosition
        for (rowNumber in currentRow downTo 1) {
            input[rowNumber][currentColumn] = 'X'
            if (input[rowNumber - 1][currentColumn] == '#' ||
                input[rowNumber - 1][currentColumn] in listOf('U', 'R', 'D', 'L') // For Part 2
            ) {
                return Pair(rowNumber, currentColumn)
            }
        }
        input[0][currentColumn] = 'X'
        return Pair(0, currentColumn)
    }

    // Rotate the input 90 degrees anti-clockwise
    fun rotateInput(input: List<MutableList<Char>>): List<MutableList<Char>> {
        val numRows = input.size
        val numCols = input[0].size

        val rotatedInput = MutableList(numCols) { mutableListOf<Char>() }

        for (col in numCols - 1 downTo 0) {
            for (row in 0..<numRows) {
                rotatedInput[numCols - col - 1].add(input[row][col])
            }
        }

        return rotatedInput
    }

    fun transformPosition(numCols: Int, currentPosition: Pair<Int, Int>): Pair<Int, Int> {
        val (row, col) = currentPosition
        return Pair(numCols - col - 1, row)
    }

    fun isValidPosition(input: List<MutableList<Char>>, row: Int, col: Int): Boolean {
        var nextInput = input.map { it.toMutableList() }
        nextInput[row][col] = '#'
        val currentPosition = findInitialPosition(nextInput)
        var nextPosition = findNextPosition(nextInput, currentPosition)

        val directions = listOf('U', 'R', 'D', 'L')
        var currentDirectionIdx = 0

        while (nextPosition.first != 0) {
            val (obstaclePositionRow, obstaclePositionCol) = Pair(nextPosition.first - 1, nextPosition.second)
            if (nextInput[obstaclePositionRow][obstaclePositionCol] == directions[currentDirectionIdx]) {
                return true
            }
            nextInput[obstaclePositionRow][obstaclePositionCol] = directions[currentDirectionIdx]
            currentDirectionIdx = (currentDirectionIdx + 1) % 4
            nextInput = rotateInput(nextInput)
            nextPosition = findNextPosition(nextInput, transformPosition(nextInput[0].size, nextPosition))
        }

        return false
    }

    fun part1(input: List<MutableList<Char>>): Int {
        var nextInput = input.map { it.toMutableList() }
        val currentPosition = findInitialPosition(nextInput)
        var nextPosition = findNextPosition(nextInput, currentPosition)
        while (nextPosition.first != 0) {
            nextInput = rotateInput(nextInput)
            nextPosition = findNextPosition(nextInput, transformPosition(nextInput[0].size, nextPosition))
        }
        return nextInput.sumOf { it.count { char -> char == 'X' } }
    }

    fun part2(input: List<MutableList<Char>>): Int {
        var numPositions = 0
        for (row in input.indices) {
            for (col in input[0].indices) {
                if (input[row][col] == '.' && isValidPosition(input, row, col)) {
                    numPositions++
                }
            }
        }
        return numPositions
    }

    val input = readInput("Day06").map { it.toMutableList() }
    println(part1(input))
    println(part2(input))
}