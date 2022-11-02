package model

import Character.Character

data class Position(var x: Int, var y: Int) {
    constructor(position: Position, direction: Direction) : this(position.x, position.y) {
        this.x += + direction.relativeX
        this.y += + direction.relativeY
    }
    fun isFree (gameObjects: ArrayList<GameObject>): Boolean {
        for (gameObject in gameObjects) {
            if (gameObject.position == this) {
                return false
            }
        }
        return true
    }

    fun isFree (character: Character): Boolean {
            if (character.position == this) {
                return false
            }
        return true
    }

}