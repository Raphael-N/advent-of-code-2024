fun main() {
    val input = readInput("day3").joinToString()
    val regex = Regex("mul\\((\\d+),(\\d+)\\)")
    val doRegex = Regex("do\\(\\)")
    val dontRegex = Regex("don't\\(\\)")
    val doIndeces = doRegex.findAll(input).map { it.range.first }.toList()
    val dontIndeces = dontRegex.findAll(input).map { it.range.first }.toList()
    var currentDoIndex = -1
    var currentDontIndex = -1
    val numbers = regex.findAll(input).filter {
        currentDoIndex = findMostRecentIndex(doIndeces, currentDoIndex, it.range.first)
        currentDontIndex = findMostRecentIndex(dontIndeces, currentDontIndex, it.range.first)
        if (currentDontIndex >= 0 && currentDoIndex == -1 ||
            currentDontIndex >= 0 && currentDoIndex >= 0 && dontIndeces[currentDontIndex] > doIndeces[currentDoIndex]
        ) {
            return@filter false
        }
        true
    }.map { it.groupValues.map { it.toIntOrNull() }.filterNotNull() }.toList()
    part1(numbers)
}

fun findMostRecentIndex(indeces: List<Int>, startIndex: Int, closeTo: Int): Int {
    var result = startIndex
    while (result + 1 < indeces.size && indeces[result + 1] < closeTo) {
        result++
    }
    return result
}

private fun part1(numbers: List<List<Int>>) {
    val result = numbers.sumOf {
        it.reduce { a, b -> a * b }
    }
    println(result)
}


