fun main() {

    fun Long.mix(value: Long): Long {
        return this xor value
    }

    fun Long.prune(): Long {
        return this and 16777215
    }

    fun part1(input: List<Long>): Long {
        var ans = 0L
        for (secret in input) {
            var newSecret = secret
            repeat(2000) {
                newSecret = newSecret.mix(newSecret shl 6).prune()
                newSecret = newSecret.mix(newSecret shr 5).prune()
                newSecret = newSecret.mix(newSecret shl 11).prune()
            }
            ans += newSecret
        }
        return ans
    }

    fun part2(input: List<Long>): Long {
        val priceChanges = mutableListOf<List<Pair<Long, Long>>>()
        val priceMap = mutableMapOf<List<Long>, Long>()
        for (secret in input) {
            var newSecret = secret
            var prevSecret = secret
            val buyerChanges = MutableList(2000) { -1L to -1L }
            repeat(2000) { idx ->
                newSecret = newSecret.mix(newSecret shl 6).prune()
                newSecret = newSecret.mix(newSecret shr 5).prune()
                newSecret = newSecret.mix(newSecret shl 11).prune()
                buyerChanges[idx] = newSecret % 10 to newSecret % 10 - prevSecret % 10
                prevSecret = newSecret
            }
            priceChanges.add(buyerChanges)
        }
        for (buyerChanges in priceChanges) {
            val seen = mutableSetOf<List<Long>>()
            for (i in 0..buyerChanges.size - 4) {
                val sequence = listOf(
                    buyerChanges[i].second,
                    buyerChanges[i + 1].second,
                    buyerChanges[i + 2].second,
                    buyerChanges[i + 3].second
                )
                if (sequence !in seen) priceMap[sequence] =
                    priceMap.getOrDefault(sequence, 0L) + buyerChanges[i + 3].first
                seen.add(sequence)
            }
        }
        return priceMap.values.max()
    }

    val input = readInput("Day22")
    println(part1(convertToLongListsByRow(input).map { it[0] }))
    println(part2(convertToLongListsByRow(input).map { it[0] }))
}