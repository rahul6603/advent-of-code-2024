fun main() {

    fun isValidUpdate(update: List<Int>, ordering: Map<Int, MutableSet<Int>>): Boolean {
        for (beforeIdx in 0..<update.size - 1) {
            for (afterIdx in beforeIdx + 1..<update.size) {
                if (ordering[update[beforeIdx]]?.contains(update[afterIdx]) != true) return false
            }
        }
        return true
    }

    fun getOrderingMap(input1: List<String>): Map<Int, MutableSet<Int>> {
        val orderingList = convertToIntListsByRow(input1, '|')
        val orderingMap = mutableMapOf<Int, MutableSet<Int>>()
        for ((from, to) in orderingList) {
            orderingMap.getOrPut(from) { mutableSetOf() }.add(to)
        }
        return orderingMap
    }

    fun part1(input1: List<String>, input2: List<String>): Int {
        var sum = 0
        val orderingMap = getOrderingMap(input1)
        val intUpdates = convertToIntListsByRow(input2, ',')
        intUpdates.forEach { update ->
            if (isValidUpdate(update, orderingMap)) {
                val mid = update.size / 2
                sum += update[mid]
            }
        }
        return sum
    }

    fun part2(input1: List<String>, input2: List<String>): Int {
        var sum = 0
        val orderingMap = getOrderingMap(input1)
        val intUpdates = convertToIntListsByRow(input2, ',')
        intUpdates.forEach { update ->
            if (!isValidUpdate(update, orderingMap)) {
                val newUpdate = update.toMutableList()
                for (beforeIdx in 0..<newUpdate.size - 1) {
                    for (afterIdx in beforeIdx + 1..<newUpdate.size) {
                        if (orderingMap[newUpdate[afterIdx]]?.contains(newUpdate[beforeIdx]) != true)
                            newUpdate[beforeIdx] =
                                newUpdate[afterIdx].also { newUpdate[afterIdx] = newUpdate[beforeIdx] }
                    }
                }
                val mid = newUpdate.size / 2
                sum += newUpdate[mid]
            }
        }
        return sum
    }

    val input1 = readInput("Day05_a")
    val input2 = readInput("Day05_b")
    println(part1(input1, input2))
    println(part2(input1, input2))
}