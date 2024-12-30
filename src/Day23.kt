fun main() {

    fun part1(graph: Map<String, Set<String>>): Int {
        var ans = 0
        fun isValid(node1: String, node2: String, node3: String): Boolean {
            return (node1[0] == 't' || node2[0] == 't' || node3[0] == 't') &&
                    node1 in graph[node2]!! && node1 in graph[node3]!! &&
                    node2 in graph[node1]!! && node2 in graph[node3]!! &&
                    node3 in graph[node1]!! && node3 in graph[node2]!!
        }

        val nodesList = graph.keys.toList()
        for (i in 0..<nodesList.size - 2) {
            for (j in i + 1..<nodesList.size - 1) {
                for (k in j + 1..<nodesList.size) {
                    if (isValid(nodesList[i], nodesList[j], nodesList[k])) {
                        ans++
                    }
                }
            }
        }
        return ans
    }

    fun part2(graph: Map<String, Set<String>>): String {
        val maximalCliques = mutableListOf<Set<String>>()
        fun bronKerbosch(
            currentVertices: MutableSet<String>,
            potentialVertices: MutableSet<String>,
            processedVertices: MutableSet<String>
        ) {
            if (potentialVertices.isEmpty() && processedVertices.isEmpty()) {
                maximalCliques.add(currentVertices)
            }
            for (potentialVertex in potentialVertices.toList()) {
                val newCurrentVertices = currentVertices union setOf(potentialVertex)
                val newPotentialVertices = potentialVertices intersect graph[potentialVertex]!!
                val newProcessedVertices = processedVertices intersect graph[potentialVertex]!!
                bronKerbosch(
                    newCurrentVertices.toMutableSet(),
                    newPotentialVertices.toMutableSet(),
                    newProcessedVertices.toMutableSet()
                )
                potentialVertices.remove(potentialVertex)
                processedVertices.add(potentialVertex)
            }
        }
        bronKerbosch(mutableSetOf(), graph.keys.toMutableSet(), mutableSetOf())
        return maximalCliques.sortedByDescending { it.size }[0].sorted().joinToString(",")
    }

    val input = readInput("Day23").map { it.split('-') }
    val graph = mutableMapOf<String, MutableSet<String>>()
    for ((from, to) in input) {
        graph.getOrPut(from) { mutableSetOf() }.add(to)
        graph.getOrPut(to) { mutableSetOf() }.add(from)
    }
    println(part1(graph))
    println(part2(graph))
}