package model

import Character.Enemy
import Character.Hero
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
        val bridgePosition = generateRandomPositionOnMeadow()
        for (i in 1..height - 2) {

            gamePlan[i][bridgePosition.x] = GameField(Terrain.RIVER)
        }
        gamePlan[bridgePosition.y][bridgePosition.x] = GameField(Terrain.BRIDGE)
    }


    private fun generateForest() {
        for (i in 1..area() / 10) {
            val forestPosition = generateRandomPositionOnMeadow()
            gamePlan[forestPosition.y][forestPosition.x] = GameField(Terrain.FOREST)
        }
    }

    private fun area(): Int {
        return height * width;
    }

    fun generateRandomPositionOnMeadow(): Position {
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

    fun map(hero: Hero) {
        for(i in 0 until height){
            for (j in 0 until width){
                if(Position(i,j) == Position(hero.position.x, hero.position.y)) print(" H")
                else print(gamePlan[i][j].terrain.terrainChar + " ")
            }
            println()
        }

    }

    fun generateFreeRandomPositionOnMeadow(gameObjects: ArrayList<GameObject>): Position {
        var randomPosition: Position
        do {
            randomPosition = generateRandomPositionOnMeadow()
        } while (!randomPosition.isFree(gameObjects))
        return randomPosition
    }


}