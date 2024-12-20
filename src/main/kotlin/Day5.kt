fun main() {
    val input = readInput("day5")
    val rules = input.filter { it.contains("|") }.map { it.split("|") }.groupBy({ it[0].toInt() }, { it[1].toInt() })
    val orders = input.filter { it.contains(",") }.map { it.split(",").map { it.toInt() } }
    var result = 0
    val correctOrders: MutableSet<Int> = hashSetOf()
    for (order in orders.withIndex()) {
        var allowedSet: MutableSet<Int> = HashSet()
        var goodRule = true
        order.value.forEachIndexed { index, number ->
            val isAllowedHere = allowedSet.contains(number) || index == 0
            if (index == 0) {
                allowedSet.addAll(rules[number]!!)
            } else {
                allowedSet = allowedSet.intersect((rules[number] ?: emptySet()).toSet()).toMutableSet()
            }
            goodRule = goodRule && isAllowedHere
        }
        result += if (goodRule) order.value[order.value.size / 2] else 0
        if (goodRule) {
            correctOrders.add(order.index)
        }
    }
    println(result)
    result = 0
    for (order in orders.withIndex()) {
        if (correctOrders.contains(order.index)) {
            continue
        }
        val fixedOrderRev = order.value.reversed().toMutableList()
        for (i in fixedOrderRev.indices) {
            for (j in i + 1 .. fixedOrderRev.lastIndex) {
                if (fixedOrderRev[i] in (rules[fixedOrderRev[j]] ?: emptySet())) {
                    continue
                } else {
                    val temp = fixedOrderRev[i]
                    fixedOrderRev[i] = fixedOrderRev[j]
                    fixedOrderRev[j] = temp
                }
            }
        }
        result += fixedOrderRev[fixedOrderRev.size / 2]
    }
    println(result)
}
