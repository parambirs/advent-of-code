import java.io.File

fun main() {
    // test
    val testInput = File("day01-test.txt").readText()
    day01(testInput)

    println("----------")

    // actual
    val realInput = File("day01.txt").readText()
    day01(realInput)
}

fun day01(input: String) {
    // part 1
    val groups = input.split("\n\n")

    val calories = groups.map {
        it.split("\n").sumOf { s -> if (s.isNotBlank()) s.toLong() else 0 }
    }
    println(calories.max())

    // part 2
    val topThree = calories.sortedDescending().take(3).sum()
    println(topThree)
}