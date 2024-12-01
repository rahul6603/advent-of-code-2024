import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun convertToIntLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    var firstList = mutableListOf<Int>()
    var secondList = mutableListOf<Int>()
    for (str in input) {
        val (firstElement, secondElement) = str.split("   ").map { it.toInt() }
        firstList.add(firstElement)
        secondList.add(secondElement)
    }
    return Pair(firstList, secondList)
}
