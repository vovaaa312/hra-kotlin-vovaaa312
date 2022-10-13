package model

enum class Terrain (val description: String, val terrainChar: Char){
    BORDER("hranice", '#'),
    MEADOW("louka",' '),
    FOREST("les", '|'),
    RIVER("reka",'*'),
    BRIDGE("most", '='),
    HERO("hrdina", 'H');
}