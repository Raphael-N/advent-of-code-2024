import kotlin.math.abs

fun main() {
    val testInput = readInput("day1")
    val list1: MutableList<Int> = ArrayList()
    val list2: MutableList<Int> = ArrayList()
    testInput.map { it.split("\\s+".toRegex()).map { it.toInt() } }.forEach {
        list1.add(it[0])
        list2.add(it[1])
    }
    part1(list1, list2)
    part2(list1, list2)
}

private fun part2(list1: MutableList<Int>, list2: MutableList<Int>) {
    val occurrencesInList2 = list2.groupingBy { it }.eachCount()
    var simScore = 0
    list1.forEach {
        simScore += it * occurrencesInList2.getOrDefault(it, 0)
    }
    println(simScore)
}

private fun part1(
    list1: MutableList<Int>,
    list2: MutableList<Int>
) {
    val sortedL1 = list1.sorted()
    val sortedL2 = list2.sorted()
    var totalDist = 0
    for (i in sortedL1.indices) {
        totalDist += abs(sortedL1[i] - sortedL2[i])
    }
    println(totalDist)
}