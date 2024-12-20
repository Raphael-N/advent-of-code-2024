import kotlin.math.min

private const val KEYWORD = "XMAS"

fun main() {
    val input = readInput("day4")
    val verticalStrings = buildVerticalStrings(input)
    val diagonalStrings = buildDiagonalStrings(input)
    val finalCount = listOf(input, verticalStrings, diagonalStrings.first, diagonalStrings.second).sumOf {
        search(it)
    }
    println(finalCount)
    var count = 0
    for (i in input.indices) {
        for (j in input.indices) {
            if (input[i][j] == 'A') {
                count += if (checkMas(input, i, j)) 1 else 0
            }
        }
    }
    println(count)
}

private fun checkMas(input: List<String>, i: Int, j: Int): Boolean {
    return (((input.getOrNull(i - 1)?.getOrNull(j - 1) ?: '1') == 'M' &&
            (input.getOrNull(i + 1)?.getOrNull(j + 1) ?: '1') == 'S') ||
            ((input.getOrNull(i - 1)?.getOrNull(j - 1) ?: '1') == 'S' &&
                    (input.getOrNull(i + 1)?.getOrNull(j + 1) ?: '1') == 'M')) &&
            (((input.getOrNull(i + 1)?.getOrNull(j - 1) ?: '1') == 'M' &&
                    (input.getOrNull(i - 1)?.getOrNull(j + 1) ?: '1') == 'S') ||
            ((input.getOrNull(i + 1)?.getOrNull(j - 1) ?: '1') == 'S' &&
                    (input.getOrNull(i - 1)?.getOrNull(j + 1) ?: '1') == 'M'))

}

private fun search(input: List<String>): Int {
    return input.sumOf { line ->
        line.windowed(KEYWORD.length).count { it == KEYWORD } +
                line.reversed().windowed(KEYWORD.length).count { it == KEYWORD }
    }
}

fun buildDiagonalStrings(input: List<String>): Pair<List<String>, List<String>> {
    val result1: MutableList<CharArray> = mutableListOf()
    var j = 0
    for (i in 0 until (input.size * 2 - 1)) {
        val size = if (i < input.size) {
            i
        } else {
            j++
            input.lastIndex - j
        }
        result1.add(CharArray(size + 1) { input[min(i, input.lastIndex) - it][it + j] })
    }
    val result2: MutableList<CharArray> = mutableListOf()
    j = 0
    for (i in 0 until (input.size * 2 - 1)) {
        val size = if (i < input.size) {
            i
        } else {
            j++
            input.lastIndex - j
        }
        result2.add(CharArray(size + 1) { input[input.lastIndex - min(i, input.lastIndex) + it][it + j] })
    }
    return Pair(result1.map { String(it) }, result2.map { String(it) })
}

fun buildVerticalStrings(input: List<String>): List<String> {
    val result: MutableList<CharArray> = mutableListOf()
    for (i in input.first().indices) {
        result.add(CharArray(input.size) { input[it][i] })
    }
    return result.map { String(it) }
}

