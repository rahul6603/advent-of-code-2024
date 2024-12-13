fun main() {

    val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

    fun isInsideGrid(row: Int, col: Int, input: List<String>): Boolean {
        return row >= 0 && row < input.size && col >= 0 && col < input[0].length
    }

    fun calculatePerimeter(row: Int, col: Int, currentChar: Char, input: List<String>): Int {
        var perimeter = 0
        for (direction in directions) {
            val (newRow, newCol) = Pair(row + direction.first, col + direction.second)
            if (!isInsideGrid(newRow, newCol, input) || input[newRow][newCol] != currentChar) {
                perimeter++
            }
        }
        return perimeter
    }

    fun numCorners(row: Int, col: Int, currentChar: Char, input: List<String>): Int {
        var numCorners = 0
        repeat(4) { idx ->
            val direction1 = directions[idx]
            val direction2 = directions[(idx + 1) % 4]
            val (newRow1, newCol1) = Pair(row + direction1.first, col + direction1.second)
            val (newRow2, newCol2) = Pair(row + direction2.first, col + direction2.second)
            if ((!isInsideGrid(newRow1, newCol1, input) || input[newRow1][newCol1] != currentChar) &&
                (!isInsideGrid(newRow2, newCol2, input) || input[newRow2][newCol2] != currentChar)
            ) {
                numCorners++
            }
            val (diagonalRow, diagonalCol) = Pair(
                row + direction1.first + direction2.first,
                col + direction1.second + direction2.second
            )
            if (isInsideGrid(diagonalRow, diagonalCol, input) && input[diagonalRow][diagonalCol] != currentChar &&
                isInsideGrid(newRow1, newCol1, input) && input[newRow1][newCol1] == currentChar &&
                isInsideGrid(newRow2, newCol2, input) && input[newRow2][newCol2] == currentChar
            ) {
                numCorners++
            }
        }
        return numCorners
    }

    fun dfs(
        row: Int,
        col: Int,
        currentChar: Char,
        input: List<String>,
        visited: List<MutableList<Boolean>>
    ): Triple<Int, Int, Int> {
        if (row < 0 || row >= input.size || col < 0 || col >= input[0].length ||
            input[row][col] != currentChar || visited[row][col]
        ) {
            return Triple(0, 0, 0)
        }
        visited[row][col] = true
        var area = 1
        var perimeter = calculatePerimeter(row, col, currentChar, input)
        var numEdges = numCorners(row, col, currentChar, input)
        for (direction in directions) {
            val (newRow, newCol) = Pair(row + direction.first, col + direction.second)
            val (areaOfNeighbours, perimeterOfNeighbours, numEdgesOfNeighbours) = dfs(
                newRow,
                newCol,
                currentChar,
                input,
                visited
            )
            area += areaOfNeighbours
            perimeter += perimeterOfNeighbours
            numEdges += numEdgesOfNeighbours

        }
        return Triple(area, perimeter, numEdges)
    }

    fun part1and2(input: List<String>, part1: Boolean): Int {
        var totalPrice = 0
        val visited = List(input.size) { MutableList(input[0].length) { false } }
        for (row in input.indices) {
            for (col in input[0].indices) {
                if (!visited[row][col]) {
                    val (area, perimeter, numEdges) = dfs(
                        row,
                        col,
                        input[row][col],
                        input,
                        visited
                    )
                    totalPrice += if (part1) area * perimeter else area * numEdges
                }
            }
        }
        return totalPrice
    }

    val input = readInput("Day12")
    println(part1and2(input, true))
    println(part1and2(input, false))
}