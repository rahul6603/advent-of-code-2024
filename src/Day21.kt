fun main() {

    data class Keypad(val coordinates: Map<Char, Pair<Int, Int>>, val gap: Pair<Int, Int>)

    fun createKeypad(vararg padRows: String): Keypad {
        val coordinates = mutableMapOf<Char, Pair<Int, Int>>()
        val gap = if (padRows.size == 4) 3 to 0 else 0 to 0
        padRows.forEachIndexed { row, keys ->
            keys.forEachIndexed { col, key ->
                if (key != ' ') {
                    coordinates[key] = row to col
                }
            }
        }
        return Keypad(coordinates, gap)
    }

    val numericKeypad = createKeypad("789", "456", "123", " 0A")
    val directionKeypad = createKeypad(" ^A", "<v>")

    fun shortestPath(fromKey: Char, toKey: Char, keypad: Keypad): String {
        val (r1, c1) = keypad.coordinates[fromKey]!!
        val (r2, c2) = keypad.coordinates[toKey]!!
        val vertical = if (r2 > r1) "v".repeat(r2 - r1) else "^".repeat(r1 - r2)
        val horizontal = if (c2 > c1) ">".repeat(c2 - c1) else "<".repeat(c1 - c2)

        if (c2 > c1 && r2 to c1 != keypad.gap) return vertical + horizontal + 'A'
        return if (r1 to c2 != keypad.gap) horizontal + vertical + 'A' else vertical + horizontal + 'A'
    }

    fun sequences(sequence: String, keypad: Keypad): List<String> {
        val keys = mutableListOf<String>()
        var prevKey = 'A'
        for (key in sequence) {
            keys.add(shortestPath(prevKey, key, keypad))
            prevKey = key
        }
        return keys
    }

    fun part1and2(input: List<String>, numDirRobots: Int): Long {
        fun List<String>.toFrequencyTable() = groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        return input.sumOf { code ->
            var frequencies = sequences(code, numericKeypad).toFrequencyTable()
            repeat(numDirRobots) {
                val newFrequencies = mutableMapOf<String, Long>()
                for ((sequence, freq) in frequencies.entries) {
                    for (newSeq in sequences(sequence, directionKeypad)) {
                        newFrequencies[newSeq] = newFrequencies.getOrDefault(newSeq, 0L) + freq
                    }
                }
                frequencies = newFrequencies
            }
            val sequenceLength = frequencies.entries.sumOf { (sequence, freq) -> sequence.length * freq }
            sequenceLength * code.substring(0, code.length - 1).toLong()
        }
    }

    val input = readInput("Day21")
    println(part1and2(input, 2))
    println(part1and2(input, 25))
}