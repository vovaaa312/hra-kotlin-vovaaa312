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
    WEST (0, -1, "západ", "z"),

    NORTHEAST(-1, 1, "sever-vychod", "sv"),
    NORTHWEST(-1, -1, "sever-zapad", "sz"),

    SOUTHEAST(1,1,"jih-vychod", "jv"),
    SOUTHWEST(1,-1, "jih-zapad", "jz")

    ;
}