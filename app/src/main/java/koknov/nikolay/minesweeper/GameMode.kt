package koknov.nikolay.minesweeper

enum class GameMode(var bomb: Int) {
    EASY(16),
    MEDIUM(44),
    HARD(99)
}