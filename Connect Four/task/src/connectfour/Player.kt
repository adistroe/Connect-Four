package connectfour

data class Player(val name: String, val glyph: Glyph, var score: Int = 0) {
    enum class Glyph(val symbol: Char) {
        FIRST_PLAYER('o'),
        SECOND_PLAYER('*')
    }
}