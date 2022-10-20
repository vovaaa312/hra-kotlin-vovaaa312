package Character

import model.Position
import java.lang.StringBuilder

data class Hero(
    override var name: String = "Hrdina",
    override var position: Position
) : Character() {
    var healing: Double = 0.5

    constructor(
        name: String,
        position: Position,
        health: Double,
        attack: Double,
        defense: Double,
        healing: Double
    ) : this(name, position) {
        this.health = health
        this.attack = attack
        this.defense = defense
        this.healing = healing
    }

    override fun toString(): String {
        var description = StringBuilder("")
        description.append(super.toString())
        description.append("Uzdravování: " + "%.2f".format(healing) + "\n")
        return super.toString()
    }
}