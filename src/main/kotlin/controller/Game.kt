package controller

import Character.Enemy
import Character.Hero
import model.*

class Game {
    private val width: Int = 20;
    private val height: Int = 10;
    private val numForests: Int = 7;
    private val enemiesCount = 5;
    private var command = "";
    private var possibleCommands = arrayListOf<String>()

    val enemies = arrayListOf<GameObject>()
    var gameObjects = arrayListOf<GameObject>()

    private var gamePlan = GamePlan(width, height, numForests);
    private var hero: Hero

    init {
        hero = Hero(position = gamePlan.generateRandomPositionOnMeadow());
        gameObjects.add(hero);
        generateEnemies()
    }

    private fun generateEnemies() {
        var enemy : Enemy
        repeat (enemiesCount) {
            enemy = generateEnemy()
            enemies.add(enemy)
            gameObjects.add(enemy)
        }
    }

    private fun generateEnemy(): Enemy {
        return Enemy(name="Skeleton",
            position = gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            health = 10.0,
            attack = 5.0,
            defense = 0.5)
    }

    fun run() {
        do {
            possibleCommands.clear()
            setPossibleCommands(hero)
            println("-------------------------");
            println(getSurroundingDescription())
            command = readCommand()
            // gamePlan.showHero(hero)
            println(runCommand(command))
            if (command.uppercase() == "KONEC") {
                println("Konec hry")
                break
            }
        } while (true)
    }

    fun runCommand(command: String): String {
        when (command) {
            "stav" -> return (hero.toString())
            "mapa" -> gamePlan.map()
            Direction.NORTH.command -> return hero.go(Direction.NORTH)
            Direction.EAST.command -> return hero.go(Direction.EAST)
            Direction.SOUTH.command -> return hero.go(Direction.SOUTH)
            Direction.WEST.command -> return hero.go(Direction.WEST)

        }

        return ""
    }


    fun readCommand(): String {
        println("Mozne prikazy: $possibleCommands");
        println("Zadej příkaz: ")
        return readLine().toString()
    }

    fun setPossibleCommands(hero: Hero) {
        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.NORTH.relativeX,
                    hero.position.y + Direction.NORTH.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.NORTH.command)
        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.SOUTH.relativeX,
                    hero.position.y + Direction.SOUTH.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.SOUTH.command)
        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.EAST.relativeX,
                    hero.position.y + Direction.EAST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.EAST.command)
        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.WEST.relativeX,
                    hero.position.y + Direction.WEST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.WEST.command)

        possibleCommands.add("stav")
        possibleCommands.add("mapa")
        possibleCommands.add("konec")
    }

    fun getSurroundingDescription(): String {
        val description = java.lang.StringBuilder("")
        description.append(
            "Na severu je " + gamePlan.getGameField(
                Position(hero.position, Direction.NORTH)
            ).terrain.description + ". "
        )
        description.append(
            "Na východu je " + gamePlan.getGameField(
                Position(hero.position, Direction.EAST)
            ).terrain.description + ". "
        )
        description.append(
            "Na jihu je " + gamePlan.getGameField(
                Position(hero.position, Direction.SOUTH)
            ).terrain.description + ". "
        )
        description.append(
            "Na západu je " + gamePlan.getGameField(
                Position(hero.position, Direction.WEST)
            ).terrain.description + "."
        )

        return description.toString()
    }


}