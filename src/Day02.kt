fun main() {

    fun isSafeReport(list: List<Int>): Boolean {
        return isStrictlyMonotone(list) && maximumDifferenceOfAdjacents(list) <= 3
    }

    fun part1(input: List<String>): Int {
        var safeReports = 0
        val intLists = convertToIntListsByRow(input, ' ')
        for (list in intLists) {
            if (isSafeReport(list)) safeReports++
        }
        return safeReports
    }

    fun part2(input: List<String>): Int {
        var safeReports = 0
        val intLists = convertToIntListsByRow(input, ' ')
        for (list in intLists) {
            if (isSafeReport(list)) safeReports++
            else {
                for (idx in list.indices) {
                    val newList = list.filterIndexed { i, _ -> i != idx }
                    if (isSafeReport(newList)) {
                        safeReports++
                        break
                    }
                }
            }
        }
        return safeReports
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}