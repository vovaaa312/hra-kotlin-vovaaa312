package model

data class GameField(var terrain: Terrain) {
    fun isWalkable(): Boolean {
        return when (this.terrain) {
            Terrain.MEADOW, Terrain.BRIDGE,Terrain.HERO -> true
            Terrain.BORDER, Terrain.RIVER, Terrain.FOREST -> false
        }
    }
}