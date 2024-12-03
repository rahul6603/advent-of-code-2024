import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.max

/**
 * Reads lines from the given input txt file.
 */
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

fun convertToIntListsByRow(input: List<String>): List<List<Int>> {
    val intLists = mutableListOf<MutableList<Int>>()
    for (str in input) {
        intLists.add(str.split(' ').map { it.toInt() }.toMutableList())
    }
    return intLists
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