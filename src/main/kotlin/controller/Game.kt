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
    private var score : Int = 0

    val enemies = arrayListOf<GameObject>()
    var gameObjects = arrayListOf<GameObject>()

    private var gamePlan = GamePlan(width, height, numForests);
    private var hero: Hero

    init {
        hero = Hero(position = gamePlan.generateRandomPositionOnMeadow());
        gamePlan.getGameField(hero.position)
        gameObjects.add(hero);

        generateEnemies()
    }

    fun getEnemyOnGameField (position: Position, gameObjects: ArrayList<GameObject>) : Enemy? {
        for (obj in gameObjects ) {
            if (obj is Enemy) {
                if (obj.position == position) {
                    return obj
                }
            }
        }
        return null
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
        var message = ""
        do {
            possibleCommands.clear()
            setPossibleCommands(hero)
            println("-------------------------");
            println(getSurroundingDescription())
            command = readCommand()
            println(runCommand(command))
            println (enemyAttack())
            gamePlan.map(hero, enemies)
            message = isGameFinished()
            if (command.uppercase() == "KONEC" ) {
                println("Konec hry")
                break
            }
            if (message.isNotEmpty()) {
                println (message)
                break
            }
        } while (true)
    }

    fun checkCommand(command: String):Boolean{
        for (i in possibleCommands) {
            if (command == i) {
                return true
            }
        }
        println("Tento příkaz není k dispozici")
        return false
    }
    fun runCommand(command: String): String {
        score++
        val enemy = getEnemyOnGameField(hero.position, enemies)
        if(checkCommand(command)){
            when (command) {
                "utok" -> return hero.attack(enemy!!)
                "stav" -> return (hero.toString())
                "mapa" -> gamePlan.map(hero,enemies)
                Direction.NORTH.command -> return hero.go(Direction.NORTH)
                Direction.EAST.command -> return hero.go(Direction.EAST)
                Direction.SOUTH.command -> return hero.go(Direction.SOUTH)
                Direction.WEST.command -> return hero.go(Direction.WEST)

                Direction.SOUTHWEST.command -> return hero.go(Direction.SOUTHWEST)
                Direction.SOUTHEAST.command -> return hero.go(Direction.SOUTHEAST)

                Direction.NORTHEAST.command -> return hero.go(Direction.NORTHEAST)
                Direction.NORTHWEST.command -> return hero.go(Direction.NORTHWEST)
            }
        }


        return ""
    }

    fun enemyAttack() : String {
        val enemy = getEnemyOnGameField(hero.position, enemies)

        if (enemy is Enemy) {
            if (!enemy.isDead()) {
                return(enemy.attack(hero))
            }
        }
        return ""
    }

    fun allEnemiesDead() : Boolean {
        for (enemy in enemies) {
            if (enemy is Enemy && ! enemy.isDead()) return false
        }
        return true
    }

    fun isGameFinished(): String {
        if (hero.isDead()) return "Jsi mrtvý."
        if (allEnemiesDead()) return "Všichni nepřátelé jsou mrtví. Vyhrál jsi. Potřeboval jsi $score tahů."
        if (command == "konec") return "Konec hry."
        return ""
    }

    fun readCommand(): String {
        println("Mozne prikazy: $possibleCommands");
        println("Zadej příkaz: ")
        return readLine().toString()
    }

    fun setPossibleCommands(hero: Hero) {
        val enemy = getEnemyOnGameField(hero.position, enemies)
        if (enemy != null && !enemy.isDead()) possibleCommands.add("utok");


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

        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.SOUTHWEST.relativeX,
                    hero.position.y + Direction.SOUTHWEST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.SOUTHWEST.command)

        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.SOUTHEAST.relativeX,
                    hero.position.y + Direction.SOUTHEAST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.SOUTHEAST.command)

        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.NORTHEAST.relativeX,
                    hero.position.y + Direction.NORTHEAST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.NORTHEAST.command)

        if (gamePlan.getGameField(
                Position(
                    hero.position.x + Direction.NORTHWEST.relativeX,
                    hero.position.y + Direction.NORTHWEST.relativeY
                )
            )
                .isWalkable()
        ) possibleCommands.add(Direction.NORTHWEST.command)

        possibleCommands.add("stav")
        possibleCommands.add("mapa")
        possibleCommands.add("konec")
    }

    fun getSurroundingDescription(): String {
        val description = java.lang.StringBuilder("")

        val enemy = getEnemyOnGameField(hero.position, enemies)
        if (enemy != null && !enemy.isDead()) description.append("utok");
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