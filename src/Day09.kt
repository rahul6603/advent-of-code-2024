fun main() {

    fun part1(input: List<Long>): Long {
        var currentBlockIdx = 0L
        var fileId = 0L
        val fileList = mutableListOf<Pair<Long, Long>>()
        val freeList = mutableListOf<Long>()
        for (idx in input.indices) {
            for (blockIdx in currentBlockIdx..<currentBlockIdx + input[idx]) {
                if (idx % 2 == 0) {
                    fileList.add(Pair(blockIdx, fileId))
                } else {
                    freeList.add(blockIdx)
                }
                currentBlockIdx++
            }
            fileId += if (idx % 2 == 0) 1 else 0
        }
        while (freeList.isNotEmpty() && freeList[0] < fileList[fileList.size - 1].first) {
            val removedFileId = fileList.removeLast().second
            val freeListIdx = freeList.removeFirst()
            fileList.add(0, Pair(freeListIdx, removedFileId))
        }
        return fileList.sumOf { it.first * it.second }
    }

    fun part2(input: List<Long>): Long {
        var currentBlockIdx = 0L
        var fileId = 0L
        val fileList = mutableMapOf<Long, Pair<Long, Long>>()
        val freeList = mutableListOf<Pair<Long, Long>>()
        for (idx in input.indices) {
            if (idx % 2 == 0) {
                fileList.getOrPut(fileId) { Pair(currentBlockIdx, input[idx]) }
                fileId += 1
            } else {
                freeList.add(Pair(currentBlockIdx, input[idx]))
            }
            currentBlockIdx += input[idx]
        }
        for (id in fileId - 1 downTo 0) {
            val (fileStartIdx, fileLength) = fileList.getValue(id)
            for ((idx, freeSpace) in freeList.withIndex()) {
                val (freeSpaceStartIdx, freeSpaceLength) = freeSpace
                if (fileLength <= freeSpaceLength && freeSpaceStartIdx < fileStartIdx) {
                    fileList[id] = Pair(freeSpaceStartIdx, fileLength)
                    freeList[idx] = Pair(freeSpaceStartIdx + fileLength, freeSpaceLength - fileLength)
                    break
                }
            }
        }
        var sum = 0L
        for (fileId in fileList.keys) {
            val (fileStartIdx, fileLength) = fileList.getValue(fileId)
            for (blockIdx in fileStartIdx..<fileStartIdx + fileLength) {
                sum += fileId * blockIdx
            }
        }
        return sum
    }

    val input = readInputAsOneLine("Day09").split("").filter { it != "" }.map { it.toLong() }
    println(part1(input))
    println(part2(input))
}