import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow

fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readInputAsOneLine(name: String) = Path("src/$name.txt").readText().trim()

fun convertToIntListsByColumn(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()
    for (str in input) {
        val (firstElement, secondElement) = str.split("   ").map { it.toInt() }
        firstList.add(firstElement)
        secondList.add(secondElement)
    }
    return Pair(firstList, secondList)
}

fun convertToIntListsByRow(input: List<String>, vararg delimiter: Char): List<List<Int>> {
    val intLists = mutableListOf<MutableList<Int>>()
    for (str in input) {
        intLists.add(str.split(*delimiter).filter { it != "" }.map { it.toInt() }.toMutableList())
    }
    return intLists
}

fun convertToLongListsByRow(input: List<String>, vararg delimiter: Char): List<List<Long>> {
    val longLists = mutableListOf<MutableList<Long>>()
    for (str in input) {
        longLists.add(str.split(*delimiter).filter { it != "" }.map { it.toLong() }.toMutableList())
    }
    return longLists
}

fun isStrictlyMonotone(list: List<Int>): Boolean {
    if (list.size <= 1) return true
    val flag = list[0] < list[1]
    for (idx in 0..<list.size - 1) {
        if (list[idx] < list[idx + 1] != flag || list[idx] == list[idx + 1]) return false
    }
    return true
}

fun maximumDifferenceOfAdjacents(list: List<Int>): Int {
    var maxDifference = 0
    for (idx in 0..<list.size - 1) {
        maxDifference = max(maxDifference, abs(list[idx + 1] - list[idx]))
    }
    return maxDifference
}

fun lengthOfLong(long: Long): Int {
    var count = 0
    var num = long
    while (num != 0L) {
        num /= 10
        count++
    }
    return count
}

fun splitLongIntoTwoHalves(long: Long): Pair<Long, Long> {
    val length = lengthOfLong(long)
    val firstHalf = long / 10.0.pow(length / 2)
    val secondHalf = long % 10.0.pow(length / 2)
    return Pair(firstHalf.toLong(), secondHalf.toLong())
}

fun findInitialPosition(input1: List<MutableList<Char>>, initialChar: Char): Pair<Int, Int> {
    for (row in input1.indices) {
        for (col in input1[0].indices) {
            if (input1[row][col] == initialChar) {
                return Pair(row, col)
            }
        }
    }
    return Pair(-1, -1)
}

fun findManhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>) = abs(a.first - b.first) + abs(a.second - b.second)