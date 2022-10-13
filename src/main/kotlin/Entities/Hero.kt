package Entities

import model.Direction
import model.Position

data class Hero(
    val name : String = "Hero",
    val health: Double = 100.0,
    val attack: Double = 25.0,
    val defence: Double = 25.0,
    val healing: Double = 0.5,
    var position: Position


){
    override fun toString(): String {
        val description = StringBuilder("")
        description.append("Stav hrdiny\n")
        description.append("===========\n")
        description.append("Jméno        $name \n")
        description.append("Zdraví:      "+ "%.2f".format(health) + "\n")
        description.append("Útok:        "+ "%.2f".format(attack) + "\n")
        description.append("Obrana:      "+ "%.2f".format(defence) + "\n")
        description.append("Uzdravování: "+ "%.2f".format(healing) + "\n")
        return description.toString()
    }

    fun go(direction: Direction):String{
        this.position = Position(this.position, direction);
        return "jdu na $direction"
    }
}
