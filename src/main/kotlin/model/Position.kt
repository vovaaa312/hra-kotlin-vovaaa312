package model

data class Position(var x: Int, var y: Int) {
    constructor(position: Position, direction: Direction) : this(position.x, position.y) {
        this.x += + direction.relativeX
        this.y += + direction.relativeY
    }
}