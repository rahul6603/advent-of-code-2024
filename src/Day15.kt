fun main() {

    val directions = mapOf('^' to Pair(-1, 0), '>' to Pair(0, 1), 'v' to Pair(1, 0), '<' to Pair(0, -1))

    fun part1(input1: List<MutableList<Char>>, input2: String): Int {
        val initialPosition = findInitialPosition(input1, '@')
        var (row, col) = initialPosition
        for (move in input2) {
            val direction = directions[move]!!
            val (newRow, newCol) = Pair(row + direction.first, col + direction.second)
            var (tempRow, tempCol) = Pair(newRow, newCol)
            while (input1[tempRow][tempCol] == 'O') {
                tempRow += direction.first
                tempCol += direction.second
            }
            if (input1[tempRow][tempCol] == '.') {
                input1[tempRow][tempCol] = 'O'
                input1[newRow][newCol] = '@'
                input1[row][col] = '.'
                row = newRow
                col = newCol
            }
        }
        return input1.mapIndexed { i, r -> List(r.size) { j -> if (input1[i][j] == 'O') 100 * i + j else 0 }.sum() }
            .sum()
    }

    fun part2(input1: List<MutableList<Char>>, input2: String): Int {
        val initialPosition = findInitialPosition(input1, '@')
        var (row, col) = initialPosition
        for (move in input2) {
            val direction = directions[move]!!
            val (newRow, newCol) = Pair(row + direction.first, col + direction.second)

            if (move in listOf('<', '>')) {
                var (tempRow, tempCol) = Pair(newRow, newCol)
                while (input1[tempRow][tempCol] in listOf('[', ']')) {
                    tempCol += direction.second
                }
                if (input1[tempRow][tempCol] == '.') {
                    while (input1[tempRow][tempCol - direction.second] in listOf('[', ']')) {
                        input1[tempRow][tempCol] = input1[tempRow][tempCol - direction.second]
                        tempCol -= direction.second
                    }
                    input1[newRow][newCol] = '@'
                    input1[row][col] = '.'
                    row = newRow
                    col = newCol
                }
            } else {
                fun canMoveBoxesUpOrDown(row: Int, col: Int): Boolean {
                    if (input1[row][col] == '#') return false
                    if (input1[row][col] == '.') return true
                    val leftPart = if (input1[row][col] == '[') Pair(row, col) else Pair(row, col - 1)
                    val rightPart = if (input1[row][col] == ']') Pair(row, col) else Pair(row, col + 1)
                    val (newRow1, newCol1) = Pair(leftPart.first + direction.first, leftPart.second)
                    val (newRow2, newCol2) = Pair(rightPart.first + direction.first, rightPart.second)
                    return canMoveBoxesUpOrDown(newRow1, newCol1) && canMoveBoxesUpOrDown(newRow2, newCol2)
                }

                fun moveBoxesUpOrDown(row: Int, col: Int) {
                    if (input1[row][col] == '.') return
                    val leftPart = if (input1[row][col] == '[') Pair(row, col) else Pair(row, col - 1)
                    val rightPart = if (input1[row][col] == ']') Pair(row, col) else Pair(row, col + 1)
                    val (newRow1, newCol1) = Pair(leftPart.first + direction.first, leftPart.second)
                    val (newRow2, newCol2) = Pair(rightPart.first + direction.first, rightPart.second)
                    moveBoxesUpOrDown(newRow1, newCol1)
                    moveBoxesUpOrDown(newRow2, newCol2)
                    input1[newRow1][newCol1] = input1[leftPart.first][leftPart.second]
                    input1[newRow2][newCol2] = input1[rightPart.first][rightPart.second]
                    input1[leftPart.first][leftPart.second] = '.'
                    input1[rightPart.first][rightPart.second] = '.'
                }

                if (canMoveBoxesUpOrDown(newRow, newCol)) {
                    moveBoxesUpOrDown(newRow, newCol)
                    input1[newRow][newCol] = '@'
                    input1[row][col] = '.'
                    row = newRow
                    col = newCol
                }
            }
        }
        return input1.mapIndexed { i, r -> List(r.size) { j -> if (input1[i][j] == '[') 100 * i + j else 0 }.sum() }
            .sum()
    }

    val input1 = readInput("Day15_a").map { it.toMutableList() }
    val input2 = readInputAsOneLine("Day15_b").filter { it != '\n' }
    val input1Part2 = List(input1.size) { MutableList(2 * input1[0].size) { ' ' } }
    for (row in input1.indices) {
        for (col in input1[0].indices) {
            input1Part2[row][2 * col] = if (input1[row][col] == 'O') '[' else input1[row][col]
            input1Part2[row][2 * col + 1] = when (input1[row][col]) {
                '@' -> '.'
                'O' -> ']'
                else -> input1[row][col]
            }
        }
    }
    println(part1(input1, input2))
    println(part2(input1Part2, input2))
}