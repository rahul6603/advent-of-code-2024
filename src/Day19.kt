fun main() {

    fun part1(patterns: List<String>, designs: List<String>): Int {
        fun backtrack(design: String, candidate: StringBuilder, candidatesTried: MutableSet<String>): Boolean {
            if (candidate.toString() == design) {
                return true
            }
            candidatesTried.add(candidate.toString())
            for (pattern in patterns) {
                val newCandidate = candidate.toString() + pattern
                if (newCandidate.length > design.length ||
                    design.substring(0, newCandidate.length) != newCandidate ||
                    newCandidate in candidatesTried
                ) continue
                candidate.append(pattern)
                if (backtrack(design, candidate, candidatesTried)) return true
                candidate.delete(candidate.length - pattern.length, candidate.length)
            }
            return false
        }
        return designs.map { if (backtrack(it, StringBuilder(), mutableSetOf())) 1 else 0 }.sum()
    }

    fun part2(patterns: List<String>, designs: List<String>): Long {
        fun backtrack(design: String, candidate: StringBuilder, memo: MutableMap<String, Long>): Long {
            if (candidate.toString() == design) {
                return 1L
            }
            if (candidate.toString() in memo) {
                return memo[candidate.toString()]!!
            }
            var ways = 0L
            for (pattern in patterns) {
                val newCandidate = candidate.toString() + pattern
                if (newCandidate.length > design.length ||
                    design.substring(0, newCandidate.length) != newCandidate
                ) continue

                candidate.append(pattern)
                ways += backtrack(design, candidate, memo)
                candidate.delete(candidate.length - pattern.length, candidate.length)
            }
            memo[candidate.toString()] = ways
            return ways
        }
        return designs.sumOf { backtrack(it, StringBuilder(), mutableMapOf()) }
    }

    val input = readInput("Day19").filter { it != "" }.map { it.split(", ") }
    println(part1(input[0], input.subList(1, input.size).map { it[0] }))
    println(part2(input[0], input.subList(1, input.size).map { it[0] }))
}