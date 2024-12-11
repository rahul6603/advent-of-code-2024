fun main() {

    val memo = List(76) { mutableMapOf<List<Long>, Long>() }

    fun part1and2(input: List<Long>, blinks: Int): Long {
        if (blinks == 0) return input.size.toLong()
        if (input in memo[blinks]) {
            return memo[blinks][input]!!
        }
        var numStones = 0L
        for (inputStone in input) {
            val listOfStones = mutableListOf(inputStone)
            var stoneIdx = 0
            while (stoneIdx < listOfStones.size) {
                val stone = listOfStones[stoneIdx]
                when {
                    stone == 0L -> listOfStones[stoneIdx] = 1L
                    lengthOfLong(stone) % 2 == 0 -> {
                        val (firstHalf, secondHalf) = splitLongIntoTwoHalves(stone)
                        listOfStones.add(stoneIdx + 1, secondHalf)
                        listOfStones[stoneIdx] = firstHalf
                        stoneIdx++
                    }

                    else -> listOfStones[stoneIdx] *= 2024L
                }
                stoneIdx++
            }
            numStones += part1and2(listOfStones, blinks - 1)
        }
        memo[blinks][input] = numStones
        return numStones
    }

    val input = readInputAsOneLine("Day11").split(" ").map { it.toLong() }
    println(part1and2(input, 25))
    println(part1and2(input, 75))
}