import kotlin.math.max

fun main() {

    fun addAntinodesWithinBounds(
        firstPosition: Pair<Int, Int>,
        secondPosition: Pair<Int, Int>,
        numRows: Int,
        numColumns: Int,
        antinodePositions: MutableSet<Pair<Int, Int>>,
        part1: Boolean
    ) {
        val rowChange = secondPosition.first - firstPosition.first
        val colChange = secondPosition.second - firstPosition.second
        val range = if (part1) listOf(-1, 2) else -max(numRows, numColumns)..max(numRows, numColumns)
        for (distance in range) {
            val antinode = Pair(firstPosition.first + distance * rowChange, firstPosition.second + distance * colChange)
            if (antinode.first in 0..<numRows && antinode.second in 0..<numColumns) {
                antinodePositions.add(antinode)
            }
        }
    }

    fun part1and2(input: List<String>, part1: Boolean): Int {
        val antinodePositions = mutableSetOf<Pair<Int, Int>>()
        val numRows = input.size
        val numColumns = input[0].length
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (row in input.indices) {
            for (col in input[0].indices) {
                if (input[row][col].isLetterOrDigit()) {
                    antennas.getOrPut(input[row][col]) { mutableListOf() }.add(Pair(row, col))
                }
            }
        }
        for (char in antennas.keys) {
            val positions = antennas.getValue(char)
            for (first in 0..<positions.size - 1) {
                for (second in first + 1..<positions.size) {
                    addAntinodesWithinBounds(
                        positions[first],
                        positions[second],
                        numRows,
                        numColumns,
                        antinodePositions,
                        part1
                    )
                }
            }
        }
        return antinodePositions.size
    }

    val input = readInput("Day08")
    println(part1and2(input, true))
    println(part1and2(input, false))
}