import PlayChoice.*
import java.io.File

enum class PlayChoice {
    Rock, Paper, Scissor
}

enum class RoundTarget {
    Win, Lose, Draw
}
fun main() {
    // test
    val testInput = File("inputs/day02-test.txt").readLines()
    day02Part1(testInput)
    println("----------")
    day02Part2(testInput)

    println("----------------------------------------")

    // actual
    val realInput = File("inputs/day02.txt").readLines()
    day02Part1(realInput)
    println("----------")
    day02Part2(realInput)
}

fun day02Part1(input: List<String>) {
        val plays = input.map { line ->
            val parsed: List<PlayChoice> = line.split(' ').map { s ->
                when (s) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    "C", "Z" -> Scissor
                    else -> throw RuntimeException("Invalid input: $s")
                }
            }
            Pair(parsed[0], parsed[1])
        }
    println(plays)

    fun yourScore(choice: PlayChoice) = when (choice) {
        Rock -> 1
        Paper -> 2
        Scissor -> 3
    }

    fun roundScore(yourChoice: PlayChoice, opponentChoice: PlayChoice) = when (Pair(yourChoice, opponentChoice)) {
        Pair(Rock, Paper), Pair(Paper, Scissor), Pair(Scissor, Rock) -> 6
        Pair(Rock, Rock), Pair(Paper, Paper), Pair(Scissor, Scissor) -> 3
        Pair(Paper, Rock), Pair(Scissor, Paper), Pair(Rock, Scissor) -> 0
        else -> throw RuntimeException("Unhandled condition, yourChoice: $yourChoice, opponentChoice: $opponentChoice")
    }

    val scores = plays.map { play -> yourScore(play.second) + roundScore(play.first, play.second) }
    println(scores)
    val totalScore = scores.sum()
    println(totalScore)
}

fun day02Part2(input: List<String>) {
    val plays: List<Pair<PlayChoice, RoundTarget>> = input.map { line ->
        val parsed: List<String> = line.split(' ')
        val first: PlayChoice = when (parsed[0]) {
            "A" -> Rock
            "B" -> Paper
            "C" -> Scissor
            else -> throw RuntimeException("Invalid input: $line")
        }
        val second: RoundTarget = when (parsed[1]) {
            "X" -> RoundTarget.Lose
            "Y" -> RoundTarget.Draw
            "Z" -> RoundTarget.Win
            else -> throw RuntimeException("Invalid input: $line")
        }
        Pair(first, second)
    }
    println(plays)

    fun yourScore(choice: PlayChoice) = when (choice) {
        Rock -> 1
        Paper -> 2
        Scissor -> 3
    }

    fun roundScore(round: Pair<PlayChoice, PlayChoice>) = when (round) {
        Pair(Rock, Paper), Pair(Paper, Scissor), Pair(Scissor, Rock) -> 6
        Pair(Rock, Rock), Pair(Paper, Paper), Pair(Scissor, Scissor) -> 3
        Pair(Paper, Rock), Pair(Scissor, Paper), Pair(Rock, Scissor) -> 0
        else -> throw RuntimeException("Unhandled condition: $round")
    }

    fun yourPlay(opponentChoice: PlayChoice, roundTarget: RoundTarget): PlayChoice {
        return when (roundTarget) {
            RoundTarget.Draw -> opponentChoice  // play whatever the opponent did
            RoundTarget.Win -> when (opponentChoice) {
                Rock -> Paper
                Paper -> Scissor
                Scissor -> Rock
            }
            RoundTarget.Lose -> when (opponentChoice) {
                Rock -> Scissor
                Paper -> Rock
                Scissor -> Paper
            }
        }
    }

    val rounds = plays.map { play -> Pair(play.first, yourPlay(play.first, play.second)) }
    println(rounds)

    val scores = rounds.map { play -> yourScore(play.second) + roundScore(play) }
    println(scores)
    val totalScore = scores.sum()
    println(totalScore)
}