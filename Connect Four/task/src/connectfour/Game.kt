package connectfour

class Game {
    /**
     *  Allows player to input names
     */
    private fun getPlayerName(message: String): String {
        println(message)
        return readln()
    }

    /**
     *  Asks the player to input how many games will be played.
     *  Default is 1 game.
     */
    private fun getNumberOfGames(): Int {
        val regex = """[1-9]+""".toRegex()
        while (true) {
            println(Message.SINGLE_OR_MULTIPLE_GAMES.text)
            val input = readln()
            //  player pressed Enter, choosing default number of games
            if (input.isEmpty()) {
                return 1
            }
            //  check if player typed of valid number
            if (regex.matches(input)) {
                return input.toInt()
            } else {
                println(Message.INVALID_INPUT.text)
            }
        }
    }

    /**
     *  Starts and runs the game
     */
    fun run() {
        val cmdEndGame = "end"
        //  check if player input is a number
        val regex = """\d+""".toRegex()
        println(Message.GAME_TITLE.text)
        val firstPlayer = Player(getPlayerName(Message.FIRST_PLAYER_NAME.text), Player.Glyph.FIRST_PLAYER)
        val secondPlayer = Player(getPlayerName(Message.SECOND_PLAYER_NAME.text), Player.Glyph.SECOND_PLAYER)
        val board = Board()
        //  how many games will be played (default 1)
        val numberOfGames = getNumberOfGames()
        println(String.format(Message.PLAYER_VS_PLAYER.text, firstPlayer.name, secondPlayer.name))
        println(String.format(Message.BOARD_SIZE.text, board.rows, board.columns))
        println(
            if (numberOfGames == 1) Message.SINGLE_GAME.text else String.format(Message.TOTAL_GAMES.text, numberOfGames)
        )
        //  keeps track of how many games have been played
        var gameCount = 1
        //  keeps track whose turn is it
        var currentPlayer = firstPlayer
        //  main outer loop (single or multiple games)
        outer@ while (true) {
            //  print current game number (only when multiple games are played)
            if (numberOfGames > 1) println(String.format(Message.GAME_NUMBER.text, gameCount))
            //  draw the initial empty board
            board.draw()
            //  main inner loop (single game)
            while (true) {
                println(String.format(Message.PLAYERS_TURN.text, currentPlayer.name))
                val input = readln().lowercase()
                //  player chose to end the game
                if (input == cmdEndGame) {
                    break@outer
                }
                //  unrecognized column number
                if (!regex.matches(input)) {
                    println(Message.INCORRECT_COLUMN.text)
                    continue
                } else {
                    //  column number out of bounds
                    if (input.toInt() !in 1..board.columns) {
                        println(String.format(Message.COLUMN_OUT_OF_RANGE.text, 1, board.columns))
                        continue
                    }
                }
                //  place where disc will be inserted
                val index = board.board[input.toInt() - 1].indexOfFirst { it == ' ' }
                // column is full
                if (index == -1) {
                    println(String.format(Message.COLUMN_IS_FULL.text, input.toInt()))
                    continue
                } else {
                    // add player's disc to column
                    board.board[input.toInt() - 1][index] = currentPlayer.glyph.symbol
                    // print the updated board
                    board.draw()
                    // check if player won
                    if (board.playerWon(currentPlayer, Pair(input.toInt() - 1, index))) {
                        println(String.format(Message.PLAYER_WON.text, currentPlayer.name))
                        //  check if we're playing a single or multiple games
                        if (numberOfGames > 1) {
                            currentPlayer.score += 2
                            println(
                                String.format(
                                    Message.SCORE.text,
                                    firstPlayer.name,
                                    firstPlayer.score,
                                    secondPlayer.name,
                                    secondPlayer.score
                                )
                            )
                            gameCount++
                            // switch players
                            currentPlayer = if (currentPlayer == firstPlayer) secondPlayer else firstPlayer
                            //  prepare the board for next game
                            board.reset()
                        }
                        break
                    }
                    // board is full
                    if (board.isFull()) {
                        println(Message.IT_IS_DRAW.text)
                        //  check if we're playing a single or multiple games
                        if (numberOfGames > 1) {
                            firstPlayer.score++
                            secondPlayer.score++
                            println(
                                String.format(
                                    Message.SCORE.text,
                                    firstPlayer.name,
                                    firstPlayer.score,
                                    secondPlayer.name,
                                    secondPlayer.score
                                )
                            )
                            gameCount++
                            // switch players
                            currentPlayer = if (currentPlayer == firstPlayer) secondPlayer else firstPlayer
                            //  prepare the board for next game
                            board.reset()
                        }
                        break
                    }
                    // switch players at the end of each turn
                    currentPlayer = if (currentPlayer == firstPlayer) secondPlayer else firstPlayer
                    continue
                }
            }
            //  we played the last game in a series of multiple games
            if (gameCount > numberOfGames) {
                break
            }
        }
        //  Bye-bye !
        println(Message.GAME_OVER.text)
    }
}