package Character

import model.Direction
import model.GameObject

abstract class Character : GameObject() {
    open var health: Double = 100.0
    open var attack: Double = 1.2
    open var defense: Double = 1.0

    var kills: Int = 0
    fun isDead (): Boolean {
        return (health < 0.00001)
    }
    override fun toString(): String {
        val description = StringBuilder("")
        description.append("Stav hrdiny\n")
        description.append("===========\n")
        description.append("Jméno        $name \n")
        description.append("Zdraví:      " + "%.2f".format(health) + "\n")
        description.append("Útok:        " + "%.2f".format(attack) + "\n")
        description.append("Obrana:      " + "%.2f".format(defense) + "\n")
       // description.append("Uzdravování: " + "%.2f".format(healing) + "\n")
        description.append("Zabití:      $kills \n")
        return description.toString()
    }

    open fun attack (enemy: Character) : String {
        var realAttack = attack - enemy.defense
        if (realAttack < 0) realAttack=0.0
        enemy.health -= realAttack

        if (enemy.isDead()) return "${enemy.name} je mrtvý."
        return "$name zaútočil silou " + "%.2f".format(realAttack) +"."
    }
    fun go (direction: Direction) : String {
        position.x += direction.relativeX
        position.y += direction.relativeY
        return "Jdu na ${direction.description}"
    }

}