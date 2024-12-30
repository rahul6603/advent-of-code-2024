import java.io.DataInput

fun main() {

    fun searchHorizontalOrVertical(current: String, maximum: Int): Int {
        var count = 0
        for (idx in 0..<maximum - 3) {
            if (current.substring(idx, idx + 4) == "XMAS")
                count++
        }
        return count
    }

    fun searchDiagonal(input: List<String>, currentRow: Int, currentColumn: Int, numRows: Int, numColumns: Int): Int {
        var count = 0
        val directions = listOf(Triple(1, 2, 3), Triple(-1, -2, -3))
        for ((dx1, dx2, dx3) in directions) {
            for ((dy1, dy2, dy3) in directions) {
                if (currentRow + dx3 in 0..<numRows && currentColumn + dy3 in 0..<numColumns) {
                    if (input[currentRow][currentColumn] == 'X' &&
                        input[currentRow + dx1][currentColumn + dy1] == 'M' &&
                        input[currentRow + dx2][currentColumn + dy2] == 'A' &&
                        input[currentRow + dx3][currentColumn + dy3] == 'S'
                    )
                        count++
                }
            }
        }
        return count
    }

    fun part1(input: List<String>): Int {
        var count = 0
        val numRows = input.size
        val numColumns = input[0].length
        for (currentRow in 0..<numRows) {
            val currentRowString = input[currentRow]
            count += searchHorizontalOrVertical(currentRowString, numRows) +
                    searchHorizontalOrVertical(currentRowString.reversed(), numRows)
        }
        for (currentColumn in 0..<numColumns) {
            val currentColumnString = input.map { it[currentColumn] }.joinToString("")
            count += searchHorizontalOrVertical(currentColumnString, numColumns) +
                    searchHorizontalOrVertical(currentColumnString.reversed(), numColumns)
        }
        for (currentRow in 0..<numRows)
            for (currentColumn in 0..<numColumns)
                count += searchDiagonal(input, currentRow, currentColumn, numRows, numColumns)
        return count
    }

    fun isValidX(input: List<String>, currentRow: Int, currentColumn: Int, numRows: Int, numColumns: Int): Boolean {
        if (currentRow !in 1..<numRows - 1 || currentColumn !in 1..<numColumns - 1) return false
        return listOf(
            input[currentRow + 1][currentColumn + 1],
            input[currentRow + 1][currentColumn - 1],
            input[currentRow - 1][currentColumn - 1],
            input[currentRow - 1][currentColumn + 1]
        ).count { it == 'M' || it == 'S' } == 4 &&
                input[currentRow + 1][currentColumn + 1] != input[currentRow - 1][currentColumn - 1] &&
                input[currentRow + 1][currentColumn - 1] != input[currentRow - 1][currentColumn + 1]
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val numRows = input.size
        val numColumns = input[0].length
        for (currentRow in 0..<numRows)
            for (currentColumn in 0..<numColumns)
                if (input[currentRow][currentColumn] == 'A' &&
                    isValidX(input, currentRow, currentColumn, numRows, numColumns)
                )
                    count++

        return count
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}