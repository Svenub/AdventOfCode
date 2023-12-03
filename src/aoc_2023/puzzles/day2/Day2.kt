package aoc_2023.puzzles.day2

import java.io.FileReader

enum class Color {
    Blue,
    Red,
    Green
}

data class Cube(
    val color: Color
)

data class Set(
    val cubes: MutableList<Pair<Int, Cube>> = mutableListOf()
)

data class Game(
    val id: Int,
    val sets: MutableList<Set> = mutableListOf()
)

fun Cube.isRed():Boolean {
    return this.color == Color.Red
}

fun Cube.isBlue():Boolean {
    return this.color == Color.Blue
}

fun Cube.isGreen():Boolean {
    return this.color == Color.Green
}

fun main() {

    val fr = FileReader("src/aoc_2023/puzzles/day2/input.txt")
    val input: MutableList<Game> = mutableListOf()

    fr.readLines().forEach { string ->
        serializeInput(string)?.let {
            input.add(it)
        }
    }

    println("Puzzle 1: ${solve1(input)}")
    println("Puzzle 2: ${solve2(input)}")
}


fun serializeInput(string: String): Game? {
    val serializedText = string.replace(":", "").replace(",", "").replace(";", " -")
    val stringList = serializedText.split(" ")
    var game: Game? = null
    var set = Set()
    var index = 0
    while (true) {
        if (stringList[index] == "Game") {
            game = Game(id = stringList[index + 1].toInt())
            index++
        } else if (stringList[index].matches("[0-9]+".toRegex())) {
            when (stringList[index + 1]) {
                "blue" -> set.cubes.add(Pair(stringList[index].toInt(), Cube(Color.Blue)))
                "red" -> set.cubes.add(Pair(stringList[index].toInt(), Cube(Color.Red)))
                "green" -> set.cubes.add(Pair(stringList[index].toInt(), Cube(Color.Green)))
            }
        } else if (stringList[index].matches("-".toRegex())) {
            game?.sets?.add(set)
            set = Set()
        }

        index++
        if (stringList.size - 1 == index) break
    }
    game?.sets?.add(set)
    return game
}

fun solve1(games: List<Game>): Int {
    val red = 12
    val green = 13
    val blue = 14

    val sum =
        games.filter { game ->
            game.sets.all { set ->
                set.cubes.all { cube ->
                    cube.first <= red && cube.second.color == Color.Red ||
                            cube.first <= green && cube.second.color == Color.Green ||
                            cube.first <= blue && cube.second.color == Color.Blue
                }
            }
        }

    println(sum)

    return sum.sumOf { it.id }
}

fun solve2(games: List<Game>): Int {
    val powers = mutableListOf<Int>()
    var highestRed = 0
    var highestGreen = 0
    var highestBlue = 0

    games.forEach { game ->
        game.sets.forEach { set ->
            set.cubes.forEach { cube ->
                if(cube.second.isRed() && cube.first > highestRed) highestRed = cube.first
                if(cube.second.isGreen() && cube.first > highestGreen) highestGreen = cube.first
                if(cube.second.isBlue() && cube.first > highestBlue) highestBlue = cube.first
            }
        }
        powers.add(highestRed*highestGreen*highestBlue)
        highestRed = 0
        highestGreen = 0
        highestBlue = 0
    }

    return powers.sumOf { it }
}