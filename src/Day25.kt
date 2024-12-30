fun main() {

    fun part1(locks: List<List<Int>>, keys: List<List<Int>>): Int {
        var overlapping = 0
        for (lock in locks) {
            for (key in keys) {
                for (i in 0..4) {
                    if (lock[i] + key[i] > 5) {
                        overlapping++
                        break
                    }
                }
            }
        }
        return locks.size * keys.size - overlapping
    }

    val input = readInput("Day25").filter { it != "" }
    val locks = mutableListOf<List<Int>>()
    val keys = mutableListOf<List<Int>>()

    for (i in input.indices step 7) {
        val lockOrKey = MutableList(5) { -1 }
        for (k in 0..4) {
            var height = 0
            for (j in i..i + 6) {
                if (input[j][k] == '#') height++
            }
            lockOrKey[k] = height - 1
        }
        (if (input[i][0] == '#') locks else keys).add(lockOrKey)
    }
    println(part1(locks, keys))
}