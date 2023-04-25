package connectfour

enum class Message(val text: String) {
    GAME_TITLE("Connect Four"),
    FIRST_PLAYER_NAME("First player's name:"),
    SECOND_PLAYER_NAME("Second player's name:"),
    SET_BOARD_DIMENSIONS(
        "Set the board dimensions (Rows x Columns)\n" +
                "Press Enter for default " +
                "(${Board.Dimensions.DEFAULT_ROWS.value} x ${Board.Dimensions.DEFAULT_COLUMNS.value})"
    ),
    PLAYER_VS_PLAYER("%s VS %s"),
    BOARD_SIZE("%d X %d board"),
    PLAYERS_TURN("%s's turn:"),
    GAME_OVER("Game over!"),
    COLUMN_OUT_OF_RANGE("The column number is out of range (%d - %d)"),
    INCORRECT_COLUMN("Incorrect column number"),
    COLUMN_IS_FULL("Column %d is full"),
    INVALID_ROWS("Board rows should be from ${Board.Dimensions.MIN.value} to ${Board.Dimensions.MAX.value}"),
    INVALID_COLUMNS("Board columns should be from ${Board.Dimensions.MIN.value} to ${Board.Dimensions.MAX.value}"),
    INVALID_INPUT("Invalid input"),
    IT_IS_DRAW("It is a draw"),
    PLAYER_WON("Player %s won"),
    SINGLE_OR_MULTIPLE_GAMES(
        "Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter\n" +
                "Input a number of games:"
    ),
    SINGLE_GAME("Single game"),
    TOTAL_GAMES("Total %d games"),
    GAME_NUMBER("Game #%d"),
    SCORE("Score\n%s: %d %s: %d")
}