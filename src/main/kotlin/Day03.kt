import java.io.File
fun main() {
    // test
    val testInput = File("inputs/day03-test.txt").readLines()
    day03Part1(testInput)
    day03Part2(testInput)

    println("----------")

    // actual
    val realInput = File("inputs/day03.txt").readLines()
    day03Part1(realInput)
    day03Part2(realInput)
}

fun day03Part1(input: List<String>) {
    data class Rucksack(val compartment1: String, val compartment2: String) {
        fun commonItem(): Char {
            val chars1 = compartment1.toSet()
            val chars2 = compartment2.toSet()
            return chars1.intersect(chars2).first()
        }
    }

    val rucksacks = input.map { line ->
        val length = line.length
        Rucksack(line.substring(0, length/2), line.substring(length/2))
    }

    println(rucksacks.sumOf { it.commonItem().priority() })
}

fun day03Part2(input: List<String>) {
    val badges = input.windowed(3, 3)
        .map { group ->
            val (first, second, third) = group
            first.toSet().intersect(second.toSet()).intersect(third.toSet()) }
        .map { it.first() }

    println(badges.sumOf { it.priority() })
}

fun Char.priority(): Int {
    return when (this) {
        in 'a'..'z' -> 1 + (this - 'a')
        in 'A'..'Z' -> 27 + (this - 'A')
        else -> throw RuntimeException("Unexpected input: $this")
    }
}