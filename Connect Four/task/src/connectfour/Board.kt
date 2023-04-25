package connectfour

class Board {
    //  ASCII characters used to draw the game board
    enum class Glyph(val symbol: Char) {
        VERTICAL('║'),
        HORIZONTAL('═'),
        MIDDLE('╩'),
        LEFT_CORNER('╚'),
        RIGHT_CORNER('╝')
    }

    enum class Dimensions(val value: Int) {
        DEFAULT_ROWS(6),
        DEFAULT_COLUMNS(7),
        MIN(5),
        MAX(9)
    }

    var rows = Dimensions.DEFAULT_ROWS.value
    var columns = Dimensions.DEFAULT_COLUMNS.value

    /**
     *  Sets board dimensions according to player input
     */
    private fun setDimensions() {
        //  used to check if player input is in correct format
        val regex = """^(\d+)\s*[xX]\s*(\d+)$""".toRegex()
        var input: String
        while (true) {
            println(Message.SET_BOARD_DIMENSIONS.text)
            input = readln().trim()
            //  player pressed Enter, choosing default board dimensions
            if (input.isEmpty()) {
                break
            }
            val matchResult = regex.find(input)
            if (matchResult != null) {
                val inputRows = matchResult.groupValues[1].toInt()
                //  player input rows is out of bounds
                if (inputRows !in Dimensions.MIN.value..Dimensions.MAX.value) {
                    println(Message.INVALID_ROWS.text)
                    continue
                }
                val inputColumns = matchResult.groupValues[2].toInt()
                //  player input columns is out of bounds
                if (inputColumns !in Dimensions.MIN.value..Dimensions.MAX.value) {
                    println(Message.INVALID_COLUMNS.text)
                    continue
                }
                rows = inputRows
                columns = inputColumns
                break
            } else {
                //  player input is in incorrect format
                println(Message.INVALID_INPUT.text)
            }
        }
    }

    val board: List<MutableList<Char>>

    init {
        setDimensions()
        //  initialize the board as empty
        board = List(columns) { _ -> MutableList(rows) { _ -> ' ' } }
    }

    /**
     *  Column numbers at the top of the board
     */
    private fun boardHeader(): StringBuilder {
        val header = StringBuilder()
        for (number in 1..columns) {
            header.append(" $number")
        }
        return header
    }

    /**
     *  Bottom of the board
     */
    private fun boardFooter(): StringBuilder {
        val footer = StringBuilder()
        footer.append(Glyph.LEFT_CORNER.symbol)
        for (col in 1 until columns) {
            footer.append(Glyph.HORIZONTAL.symbol)
            footer.append(Glyph.MIDDLE.symbol)
        }
        footer.append(Glyph.HORIZONTAL.symbol)
        footer.append(Glyph.RIGHT_CORNER.symbol)
        return footer
    }

    /**
     *  The columns of the board
     */
    private fun boardColumns(): StringBuilder {
        val boardColumns = StringBuilder()
        for (row in rows - 1 downTo 0) {
            boardColumns.append(Glyph.VERTICAL.symbol)
            for (col in 0 until columns) {
                boardColumns.append(board[col][row])
                boardColumns.append(Glyph.VERTICAL.symbol)
            }
            if (row > 0) {
                boardColumns.append("\n")
            }
        }
        return boardColumns
    }

    /**
     *  Draws the entire board
     */
    fun draw() {
        println("${boardHeader()}\n" + "${boardColumns()}\n" + boardFooter())
    }

    /**
     *  Checks if the board is full of discs
     */
    fun isFull(): Boolean {
        for (col in board) {
            //  check columns until we find an empty place
            if (col.find { it == ' ' } != null) return false
        }
        return true
    }

    /**
     *  Resets the board to empty state
     */
    fun reset() {
        board.forEach { it.fill(' ') }
    }

    /**
     *  Checks if a player won after each disc inserted by:
     *  - generating all possible disc placements for player win: horizontally, vertically, and diagonally.
     *  - filtering out of bounds placements.
     *  - checking the remaining valid placements for four identical adjacent discs.
     */
    fun playerWon(player: Player, lastMove: Pair<Int, Int>): Boolean {
        val col = lastMove.first
        val row = lastMove.second
        //  generating all possible placements
        listOf(
            listOf(Pair(col - 2, row), Pair(col - 1, row), Pair(col + 1, row)),   // horizontal xx_x
            listOf(Pair(col + 2, row), Pair(col + 1, row), Pair(col - 1, row)),   // horizontal x_xx
            listOf(Pair(col - 3, row), Pair(col - 2, row), Pair(col - 1, row)),   // horizontal xxx_
            listOf(Pair(col + 3, row), Pair(col + 2, row), Pair(col + 1, row)),   // horizontal _xxx
            listOf(Pair(col, row - 2), Pair(col, row - 1), Pair(col, row + 1)),   // vertical xx_x
            listOf(Pair(col, row + 2), Pair(col, row + 1), Pair(col, row - 1)),   // vertical x_xx
            listOf(Pair(col, row - 1), Pair(col, row - 2), Pair(col, row - 3)),   // vertical _xxx
            listOf(Pair(col, row + 1), Pair(col, row + 2), Pair(col, row + 3)),   // vertical xxx_
            listOf(Pair(col - 2, row + 2), Pair(col - 1, row + 1), Pair(col + 1, row - 1)),   // left-diagonal x_xx
            listOf(Pair(col + 2, row - 2), Pair(col + 1, row - 1), Pair(col - 1, row + 1)),   // left-diagonal xx_x
            listOf(Pair(col - 3, row + 3), Pair(col - 2, row + 2), Pair(col - 1, row + 1)),   // left-diagonal xxx_
            listOf(Pair(col + 3, row - 3), Pair(col + 2, row - 2), Pair(col + 1, row - 1)),   // left-diagonal _xxx
            listOf(Pair(col + 2, row + 2), Pair(col + 1, row + 1), Pair(col - 1, row - 1)),   // right-diagonal xx_x
            listOf(Pair(col - 2, row - 2), Pair(col - 1, row - 1), Pair(col + 1, row + 1)),   // right-diagonal x_xx
            listOf(Pair(col + 3, row + 3), Pair(col + 2, row + 2), Pair(col + 1, row + 1)),   // right-diagonal _xxx
            listOf(Pair(col - 3, row - 3), Pair(col - 2, row - 2), Pair(col - 1, row - 1)),   // right-diagonal xxx_
        ).forEach { list ->
            //  checking if the player inserted disc is adjacent to 3 other identical discs
            if (list.count { pair ->
                    //  checking for out of bounds placements
                    pair.first in 0 until columns
                            && pair.second in 0 until rows
                            //  adjacent discs must be identical to player's disc
                            && board[pair.first][pair.second] == player.glyph.symbol
                } == 3) return true
        }
        return false
    }
}