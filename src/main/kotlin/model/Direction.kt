package model

enum class Direction (
    val relativeY : Int,
    val relativeX:Int,
    val description:String,
    val command : String
    )
{
    NORTH (-1, 0, "sever", "s"),
    SOUTH (1, 0, "jih", "j"),
    EAST (0, 1, "východ", "v"),
    WEST (0, -1, "západ", "z")
}