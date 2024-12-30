enum class Operator { ADD, MULTIPLY, CONCATENATE }

fun main() {

    fun part1and2(input: List<String>, part1: Boolean): Long {
        val validTestValues = mutableSetOf<Long>()
        val longLists = convertToLongListsByRow(input, ' ', ':')
        for (list in longLists) {
            val testValue = list[0]
            val remainingValues = list.slice(1..<list.size)
            fun backtrack(operators: MutableList<Operator>, evaluatedValue: Long, currentIdx: Int) {
                if (operators.size == remainingValues.size - 1) {
                    if (evaluatedValue == testValue) {
                        validTestValues.add(testValue)
                    }
                    return
                }
                val validOperators = if (part1) listOf(Operator.ADD, Operator.MULTIPLY)
                else listOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCATENATE)
                for (operator in validOperators) {
                    val newEvaluatedValue =
                        when (operator) {
                            Operator.ADD -> evaluatedValue + remainingValues[currentIdx]
                            Operator.MULTIPLY -> evaluatedValue * remainingValues[currentIdx]
                            Operator.CONCATENATE -> (evaluatedValue.toString() + remainingValues[currentIdx].toString()).toLong()
                        }
                    if (newEvaluatedValue <= testValue) {
                        operators.add(operator)
                        backtrack(operators, newEvaluatedValue, currentIdx + 1)
                        operators.removeLast()
                    }
                }
            }
            backtrack(mutableListOf(), remainingValues[0], 1)
        }
        return validTestValues.sum()
    }

    val input = readInput("Day07")
    println(part1and2(input, true))
    println(part1and2(input, false))
}