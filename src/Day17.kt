fun main() {

    fun part1(registers: MutableList<Int>, program: List<Int>): String {
        fun getComboOperandValue(operand: Int): Int {
            return when (operand) {
                4 -> registers[0]
                5 -> registers[1]
                6 -> registers[2]
                else -> operand
            }
        }

        val output = StringBuilder()
        var instructionPointer = 0
        while (instructionPointer < program.size) {
            val opcode = program[instructionPointer]
            val literalOperandValue = program[instructionPointer + 1]
            val comboOperandValue = getComboOperandValue(program[instructionPointer + 1])
            when (opcode) {
                0 -> registers[0] = registers[0] shr comboOperandValue
                1 -> registers[1] = registers[1] xor literalOperandValue
                2 -> registers[1] = comboOperandValue and 7
                3 -> instructionPointer = if (registers[0] != 0) literalOperandValue else instructionPointer + 2
                4 -> registers[1] = registers[1] xor registers[2]
                5 -> output.append(comboOperandValue and 7).append(',')
                6 -> registers[1] = registers[0] shr comboOperandValue
                7 -> registers[2] = registers[0] shr comboOperandValue
            }
            instructionPointer += if (opcode != 3) 2 else 0
        }
        return output.dropLast(1).toString()
    }

    val input = readInput("Day17").filter { it != "" }.map { it.split(": ", ",").mapNotNull { it.toIntOrNull() } }
    val registers = mutableListOf(input[0][0], input[1][0], input[2][0])
    val program = input[3]
    println(part1(registers, program))

    fun part2(programIdx: Int, regA: Long): Long {
        if (programIdx == -1) {
            return regA
        }
        for (i in 0..7) {
            val possibleRegA = (regA shl 3) + i
            if (possibleRegA and 7 xor 2 xor (possibleRegA shr (possibleRegA and 7 xor 2).toInt()) xor 7 and 7 == program[programIdx].toLong()) {
                val temp = part2(programIdx - 1, possibleRegA)
                if (temp == -1L) continue
                return temp
            }
        }
        return -1L
    }

    println(part2(program.size - 1, 0))
}