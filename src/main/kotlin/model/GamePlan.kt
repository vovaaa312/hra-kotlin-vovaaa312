package model

import Entities.Hero
import controller.Game
import kotlin.random.Random

data class GamePlan(val width: Int = 20, val height: Int = 10, val numForest: Int = 4) {
    private var gamePlan = Array(height) { Array(width) { GameField(Terrain.MEADOW) } }

    init {
        generateGamePlan();
    }

    private fun generateGamePlan() {
        generateBorders();
        generateForest();
        generateRiver();
    }

    private fun generateRiver() {
        val bridgePosition = generateRandomPosition()
        for (i in 1..height - 2) {

            gamePlan[i][bridgePosition.x] = GameField(Terrain.RIVER)
        }
        gamePlan[bridgePosition.y][bridgePosition.x] = GameField(Terrain.BRIDGE)
    }

//    public fun showHero(hero: Hero) {
//        var heroPos: Position = hero.position;
//        gamePlan[heroPos.y][heroPos.x] = GameField(Terrain.HERO)
//    }

    private fun generateForest() {
        for (i in 1..area() / 10) {
            val forestPosition = generateRandomPosition()
            gamePlan[forestPosition.y][forestPosition.x] = GameField(Terrain.FOREST)
        }
    }

    private fun area(): Int {
        return height * width;
    }

    fun generateRandomPosition(): Position {
        return Position(generateRandomCoord(width), generateRandomCoord(height))
    }

    private fun generateRandomCoord(size: Int): Int {
        return Random.nextInt(1, size - 1)
    }

    private fun generateBorders() {
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1) {
                    gamePlan[i][j] = GameField(Terrain.BORDER)
                }
            }
        }
    }

    fun getGameField(position: Position): GameField {
        return gamePlan[position.y][position.x]
    }

    fun map() {
        for (i in 0 until height) {
            for (j in 0 until width) {
                print(gamePlan[i][j].terrain.terrainChar + " ")
            }
            println()
        }
    }

    fun generateFreeRandomPosition(gameObjects: ArrayList<GameObject>) : Position {
        var randomPosition : Position
        do {
            randomPosition = generateRandomPosition()
        } while (! randomPosition.isFree(gameObjects) )
        return randomPosition
    }
}