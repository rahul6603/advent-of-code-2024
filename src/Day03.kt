fun main() {

    val mulRegex = Regex("""mul\([0-9]{1,3},[0-9]{1,3}\)""")
    val numbersRegex = Regex("[0-9]{1,3}")
    val doOrDontRegex = Regex("""don't\(\)|do\(\)""")

    fun part1(input: String): Int {
        var sum = 0
        val matchMul = mulRegex.findAll(input).map { it.value }
        for (mulResult in matchMul) {
            val matchNumbers = numbersRegex.findAll(mulResult).map { it.value.toInt() }
            sum += matchNumbers.elementAt(0) * matchNumbers.elementAt(1)
        }
        return sum
    }

    fun part2(input: String): Int {
        var sum = 0
        val matchResults = mulRegex.findAll(input).map { it.value to it.range }
        for (mulResult in matchResults) {
            val matchRange = mulResult.second
            val prefixString = input.substring(0, matchRange.first)
            val matchDoOrDont = doOrDontRegex.findAll(prefixString).map { it.value }
            var enabled = true
            for (doOrDontResult in matchDoOrDont) {
                enabled = doOrDontResult != "don't()"
            }
            if (enabled) {
                val matchNumbers = numbersRegex.findAll(mulResult.first).map { it.value.toInt() }
                sum += matchNumbers.elementAt(0) * matchNumbers.elementAt(1)
            }
        }
        return sum
    }

    val input = readInputAsOneLine("Day03")
    println(part1(input))
    println(part2(input))
}