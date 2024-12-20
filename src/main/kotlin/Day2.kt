fun main() {
    val input = readInput("day2")
    val values = input.map {
        it.split("\\s+".toRegex()).map { it.toInt() }
    }
    part1(values)
    part2(values)
}

private fun part1(values: List<List<Int>>) {
    var num_safe = 0
    for (line in values) {
        val sortedDescending = line.windowed(2).all { it[0] > it[1] && (it[0] - it[1] <= 3) }
        val sortedAscending = line.windowed(2).all { it[0] < it[1] && (it[1] - it[0] <= 3) }
        val sorted = sortedAscending || sortedDescending
        num_safe += if (sorted) 1 else 0
    }
    println(num_safe)
}

private fun part2(values: List<List<Int>>) {
    println()
    var num_safe = 0
    for (line in values) {
        var ascending = true
        var descending = true
        var usedBufferAscending = false
        var usedBufferDescending = false
        var prevBufferAsc = false
        var prevBufferDesc = false
        for (i in 0 until line.lastIndex) {
            var currentAscending = asc(line[i], line[i + 1])
            var currentDescending = desc(line[i], line[i + 1])

            if (prevBufferAsc) {
                currentAscending = true
                prevBufferAsc = false
            }
            if (prevBufferDesc) {
                currentDescending = true
                prevBufferDesc = false
            }

            if (!currentAscending && !usedBufferAscending) {
                usedBufferAscending = true
                // either removing previous one or removing current one fixes it?
                val res1 = asc(line.getOrElse(i - 1) { line[i + 1] - 1 }, line[i + 1])
                val res2 = asc(line[i], line.getOrElse(i + 2) { line[i] + 1 })
                prevBufferAsc = res2
                currentAscending = res1 || res2
            }

            if (!currentDescending && !usedBufferDescending) {
                usedBufferDescending = true
                // either removing previous one or removing current one fixes it?
                val res1 = desc(line.getOrElse(i - 1) { line[i + 1] + 1 }, line[i + 1])
                val res2 = desc(line[i], line.getOrElse(i + 2) { line[i] - 1 })
                prevBufferDesc = res2
                currentDescending = res1 || res2
            }
            ascending = ascending && currentAscending
            descending = descending && currentDescending
        }
        val sorted = ascending || descending
        num_safe += if (sorted) 1 else 0
    }
    println(num_safe)
}

fun asc(a: Int, b: Int): Boolean = a < b && (b - a <= 3)
fun desc(a: Int, b: Int): Boolean = a > b && (a - b <= 3)
