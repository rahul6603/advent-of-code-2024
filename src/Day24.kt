fun main() {

    fun constructZString(number: Int): String {
        return "z" + (if (number < 10) "0" else "") + number
    }

    fun part1(input1: Map<String, Long>, input2: Map<String, Pair<String, Pair<String, String>>>): Long {
        var ans = 0L
        fun dfs(wire: String): Long {
            if (wire in input1) return input1[wire]!!
            val (connection, wires) = input2[wire]!!
            val (wire1, wire2) = wires
            return when (connection) {
                "AND" -> dfs(wire1) and dfs(wire2)
                "OR" -> dfs(wire1) or dfs(wire2)
                else -> dfs(wire1) xor dfs(wire2)
            }
        }

        var current = 0
        while (constructZString(current) in input2) {
            ans += dfs(constructZString(current)) shl current++
        }
        return ans
    }

    fun part2(
        input1: Map<String, Long>,
        input2: Map<String, Pair<String, Pair<String, String>>>,
        carryMap: MutableMap<Int, String>,
        numBits: Int,
    ): MutableSet<String> {
        val ans = mutableSetOf<String>()
        var current = 1
        while (++current < numBits) { // Check for z45 manually
            //  zi = xi ^ yi ^ carry(i)
            //  carry(i) =
            //  0                                               if i = 0
            //  (xi-1 & yi-1)                                   if i = 1
            //  (xi-1 & yi-1) | (carry(i-1) & (xi-1 ^ yi-1))    if i > 1
            val (connection, wires) = input2[constructZString(current)]!!
            if (connection != "XOR") {
                ans.add(constructZString(current))
                continue
            }
            val (wire1, wire2) = wires
            if (input2[wire1]!!.first !in listOf("OR", "XOR")) {
                ans.add(wire1)
                continue
            }
            if (input2[wire2]!!.first !in listOf("OR", "XOR")) {
                ans.add(wire2)
                continue
            }
            carryMap[current] = if (input2[wire1]!!.first == "OR") wire1 else wire2
            val carry = if (input2[wire1]!!.first == "OR") input2[wire1] else input2[wire2]
            val sum = if (input2[wire1]!!.first == "XOR") input2[wire1] else input2[wire2]
            val (sumWire1, sumWire2) = sum!!.second
            if (sumWire1 !in input1 || sumWire2 !in input1) {
                ans.add(if (input2[wire1]!!.first == "XOR") wire1 else wire2)
                continue
            }
            val (carryWire1, carryWire2) = carry!!.second
            val (carryConnection1, carryWires1) = input2[carryWire1]!!
            val (carryConnection2, carryWires2) = input2[carryWire2]!!
            if (carryConnection1 != "AND") {
                ans.add(carryWire1)
                continue
            }
            if (carryConnection2 != "AND") {
                ans.add(carryWire2)
                continue
            }
            val (carryWire11, carryWire12) = carryWires1
            val firstTermCarryWires = if (carryWire11 in input1 && carryWire12 in input1) carryWires1 else carryWires2
            val secondTermCarryWires = if (carryWire11 in input1 && carryWire12 in input1) carryWires2 else carryWires1
            if (firstTermCarryWires.first !in input1 || firstTermCarryWires.second !in input1) {
                ans.add(if (firstTermCarryWires == carryWires1) carryWire1 else carryWire2)
                continue
            }
            val secondTermFirstPart =
                if (secondTermCarryWires.first == carryMap[current - 1]) secondTermCarryWires.first else secondTermCarryWires.second
            val secondTermSecondPart =
                if (secondTermCarryWires.first == carryMap[current - 1]) secondTermCarryWires.second else secondTermCarryWires.first
            if (secondTermFirstPart != carryMap[current - 1]) {
                ans.add(if (secondTermCarryWires == carryWires1) carryWire1 else carryWire2)
                continue
            }
            val (lastConnection, lastWires) = input2[secondTermSecondPart]!!
            if (lastConnection != "XOR") {
                ans.add(secondTermSecondPart)
                continue
            }
            val (lastWire1, lastWire2) = lastWires
            if (lastWire1 !in input1 || lastWire2 !in input1) {
                ans.add(secondTermSecondPart)
                continue
            }
        }
        return ans
    }

    val input1 = readInput("Day24_a").map { it.split(": ") }.associate { it[0] to it[1].toLong() }
    val input2 = readInput("Day24_b").map { it.split(" ").filter { it != "->" } }
        .associate { it[3] to (it[1] to (it[0] to it[2])) }
    println(part1(input1, input2))
    println(part2(input1, input2, mutableMapOf(1 to "nqp"), 45).sorted().joinToString(","))
}