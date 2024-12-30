import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (firstList, secondList) = convertToIntListsByColumn(input).toList().map { it.sorted() }
        var sum = 0
        for (idx in firstList.indices) {
            sum += abs(firstList[idx] - secondList[idx])
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = convertToIntListsByColumn(input).toList().map { it.sorted() }
        var sum = 0
        for (item in firstList) {
            sum += item * secondList.count { it == item }
        }
        return sum
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
