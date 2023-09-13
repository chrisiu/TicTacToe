import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args) {
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.
        //declare variables for the player names and the history
        boolean run = true;
        String[] playerNames = new String[2];
        ArrayList<char[][]> history = new ArrayList<>();
        while (run)
        {
            //display options
            System.out.println("Welcome to game of Tic Tac Toe, choose one of the following options from below:" + "\n");
            System.out.println("1. Single player");
            System.out.println("2. Two player");
            System.out.println("D. Display last match");
            System.out.println("Q. Quit");
            Scanner in = new Scanner(System.in);
            String userInput = in.next();
            if (userInput.equals("1"))
            //enter 1 player game
            {
                System.out.println("Enter player 1 name: ");
                String playerOneName = in.next();
                playerNames[0] = playerOneName;
                playerNames[1] = "Computer";
                history = runOnePlayerGame(playerNames);
            }
            else if (userInput.equals("2"))
            //enter 2 player game
            {
                System.out.println("Enter player 1 name: ");
                String playerOneName = in.next();
                System.out.println("Enter player 2 name: ");
                String playerTwoName = in.next();
                playerNames[0] = playerOneName;
                playerNames[1] = playerTwoName;
                history = runTwoPlayerGame(playerNames);
            }
            //display if there has been a game, if not tell user
            else if (userInput.equalsIgnoreCase("D"))
            {
                if (history.size() > 0)
                {
                    runGameHistory(playerNames,history);
                }
                else
                {
                    System.out.println("Error: No history to display.");
                }
            }
            //detect if the user wants to quit
            else if (userInput.equalsIgnoreCase("Q"))
            {
                run = false;
            }
            else
            {
                System.out.println("Error: Invalid Input.");
            }
        }
        //end
        System.out.println("Thanks for playing. Hope you had fun!");
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {
        // Hint: Make use of the newline character \n to get everything into one String
        // It would be best practice to do this with a loop, but since we hardcode the game to only use 3x3 boards
        // it's fine to do this without one.
        String gameBoard = "";
        for (int i = 0; i < state.length; i++)
        //loop through each row
        {
            for (int j = 0; j < state[i].length; j++)
            //loop through each element by indexing each row
            {
                //print the character there, then print a divider if it's not the last one
                gameBoard += " " + state[i][j];
                if (j != state.length - 1)
                {
                    gameBoard += " |";
                }
            }
            //print a line divider if it's not the last line
            if (i < state.length - 1 )
            {
                gameBoard += "\n" + "____________" + "\n";
            }
        }
        //returns the completed string
        return gameBoard;

    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState() {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                            {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                            {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        //simulate a coin toss using math.random
        System.out.println("Tossing a coin to see who goes first!!!");
        int coinToss = (int) (Math.random() * 2);
        String firstPlayer;
        String secondPlayer;
        char firstPlayerSymbol;
        char secondPlayerSymbol;
        if (coinToss == 1)
        //determine player order with heads or tails, and set symbols accordingly
        {
            firstPlayer = playerNames[0];
            secondPlayer = playerNames[1];
            firstPlayerSymbol = playerOneSymbol;
            secondPlayerSymbol = playerTwoSymbol;
        }
        else
        {
            firstPlayer = playerNames[1];
            secondPlayer = playerNames[0];
            firstPlayerSymbol = playerTwoSymbol;
            secondPlayerSymbol = playerOneSymbol;
        }
        System.out.println(firstPlayer + " gets to go first.");
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        //make initial board
        char[][] currentBoard = getInitialGameState();
        //add initial state to history
        gameHistory.add(getInitialGameState());
        //display initial board
        System.out.println(displayGameFromState(currentBoard));
        //make two variables: one for the turn number, and one stating the game is not over
        boolean gameOver = false;
        int turn = 1;
        //set a loop to break if the game is over
        while (!gameOver)
        {
            //determine who goes based on turn number
            if (turn % 2 != 0)
            {
                currentBoard = runPlayerMove(firstPlayer, firstPlayerSymbol, currentBoard);
            }
            else
            {
                currentBoard = runPlayerMove(secondPlayer, secondPlayerSymbol, currentBoard);
            }
            //check if the game is over every turn
            if (checkWin(currentBoard))
            {
                //should return the current player's name if it's their turn and a win is detected, meaning they won
                if (turn % 2 != 0)
                {
                    System.out.println(firstPlayer + " wins!");
                }
                else
                {
                    System.out.println(secondPlayer + " wins!");
                }
                gameOver = true;
            }
            else if (checkDraw(currentBoard))
            {
                System.out.println("It's a draw!");
                gameOver = true;
            }
            //after the player has made a move, the turn is over and is incremented
            turn++;
            //add that round to the game history list
            gameHistory.add(currentBoard);
            System.out.println(displayGameFromState(currentBoard));
        }
        return gameHistory;
    }


    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {

        System.out.println("Tossing a coin to see who goes first!!!");
        int coinToss = (int) (Math.random() * 2);
        String firstPlayer;
        String secondPlayer;
        char firstPlayerSymbol;
        char secondPlayerSymbol;
        if (coinToss == 1)
        {
            firstPlayer = playerNames[0];
            secondPlayer = playerNames[1];
            firstPlayerSymbol = playerOneSymbol;
            secondPlayerSymbol = playerTwoSymbol;
        }
        else
        {
            firstPlayer = playerNames[1];
            secondPlayer = playerNames[0];
            firstPlayerSymbol = playerTwoSymbol;
            secondPlayerSymbol = playerOneSymbol;
        }
        System.out.println(firstPlayer + " gets to go first.");
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        //make initial board
        char[][] currentBoard = getInitialGameState();
        //add initial state to history
        gameHistory.add(getInitialGameState());
        //display initial board
        System.out.println(displayGameFromState(currentBoard));
        //make two variables: one for the turn number, and one stating the game is not over
        boolean gameOver = false;
        int turn = 1;
        //set a loop to break if the game is over
        while (!gameOver)
        {
            //determine who goes based on turn number
            if (turn % 2 != 0)
            {
                if(firstPlayer != playerNames[1]) {
                    currentBoard = runPlayerMove(firstPlayer, firstPlayerSymbol, currentBoard);
                }
                else{
                    currentBoard = getCPUMove(currentBoard);
                }
            }
            else
            {
                if(secondPlayer != playerNames[1]){
                    currentBoard = runPlayerMove(secondPlayer, secondPlayerSymbol, currentBoard);
                }
                else{
                    currentBoard = getCPUMove(currentBoard);
                }
            }
            //check if the game is over every turn
            if (checkWin(currentBoard))
            {
                //should return the current player's name if it's their turn and a win is detected, meaning they won
                if (turn % 2 != 0)
                {
                    System.out.println(firstPlayer + "wins!");
                }
                else
                {
                    System.out.println(secondPlayer + "wins!");
                }
                gameOver = true;
            }
            else if (checkDraw(currentBoard))
            {
                System.out.println("It's a draw!");
                gameOver = true;
            }
            //after the player has made a move, the turn is over and is incremented
            turn++;
            //add that round to the game history list
            gameHistory.add(currentBoard);
            System.out.println(displayGameFromState(currentBoard));
        }
        return gameHistory;
    }


    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        System.out.println(playerName + "'s turn:");
        while (true) {
            //make an int array to store the player's move
            int[] move = getInBoundsPlayerMove(playerName);
            if (checkValidMove(move, currentState))
            //make the move if it is valid, else loop
            {
                char[][] newBoard = makeMove(move, playerSymbol, currentState);
                return newBoard;
            }
        }
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {
        Scanner sc = new Scanner(System.in);
        int[] playerInputs = new int[2];
        Scanner in = new Scanner(System.in);
        boolean inBounds = false;
        //set a loop to loop until we get an input that is on the board
        while (!inBounds)
        {
            System.out.print(playerName + " enter row: ");
            playerInputs[0] = in.nextInt() - 1;
            System.out.print(playerName + " enter column: ");
            //get the inputs and store them
            playerInputs[1] = in.nextInt() - 1;
            int row = playerInputs[0];
            int col = playerInputs[1];
            if (row > 2 || col > 2 || row < 0 || col < 0)
            //if invalid, tell the user
            {
                System.out.println("That row or column is out of bounds. Try again.");
            }
            else
            //or, if valid, break the loop
            {
                inBounds = true;
            }
        }
        return playerInputs;
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        int row = move[0];
        int col = move[1];
        //return whether or not that space is taken
        if (state[row][col] != emptySpaceSymbol)
        {
            System.out.println("That space is already taken. Try again.");
            return false;
        }
        else
        {
            return true;
        }
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array
        char [][] newState = new char[3][3];
        for (int i = 0; i < 3; i++)
        //copy each row of the array to a new one
        {
            newState[i] = Arrays.copyOf(currentState[i],3);
        }
        //place the player's move on the board
        int row = move[0];
        int col = move[1];
        newState[row][col] = symbol;
        return newState;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        // Horizontals
        for(int i = 0; i < 3; i++){
            if(state[i][0] != ' ' && state[i][0] == state[i][1] && state[i][1] == state[i][2]){
                return true;
            }
        }
        // Verticals
        for(int i = 0; i < 3; i++){
            if(state[0][i] != ' ' && state[0][i] == state[1][i] && state[1][i] == state[2][i]){
                return true;
            }
        }
        // Diagonals
        if(state[1][1] != ' '){
            if(state[0][0] == state[1][1] && state[1][1] == state[2][2]){
                return true;
            }
            if(state[0][2] == state[1][1] && state[1][1] == state[2][0]){
                return true;
            }
        }
        return false;
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(state[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {
        System.out.println("Computer's turn: ");
        // Hint: you can call makeMove() and not end up returning the result, in order to "test" a move
        // and see what would happen. This is one reason why makeMove() does not modify the state argument

        // Determine all available spaces
        ArrayList<int[]> valid = getValidMoves(gameState);
        // If there is a winning move available, make that move
        for(int[] space : valid){
            char[][] newState = makeMove(space, playerTwoSymbol, gameState);
            if(checkWin(newState)){
                return newState;
            }
        }
        // If not, check if opponent has a winning move, and if so, make a move there
        for(int[] space : valid){
            char[][] newState = makeMove(space, playerOneSymbol, gameState);
            if(checkWin(newState)){
                return makeMove(space, playerTwoSymbol, gameState);
            }
        }
        // If not, move on center space if possible
        if(gameState[1][1] == ' '){
            return makeMove(new int[]{1, 1}, playerTwoSymbol, gameState);
        }
        // If not, move on corner spaces if possible
        for (int[] space : valid) {
            if ((space[0] == 0 || space[0] == 2) && (space[1] == 0 || space[1] == 2)) {
                return makeMove(space, playerTwoSymbol, gameState);
            }
        }
        // Otherwise, move in any available spot

        int randomin = (int) (Math.random() * valid.size());
        int[] random = valid.get(randomin);
        return makeMove(random, playerTwoSymbol, gameState);
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> unclaimed = new ArrayList<int[]>();
        for(int i = 0; i < gameState.length; i++){
            for(int j = 0; j < gameState.length; j++){
                if(gameState[i][j] == ' '){
                    int[] move = {i, j};
                    unclaimed.add(move);
                }
            }
        }
        return unclaimed;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {
        // We have the names of the players in the format [playerOneName, playerTwoName]
        // Player one always gets 'X' while player two always gets 'O'
        // However, we do not know yet which player went first, but we'll need to know...
        // Hint for the above: which symbol appears after one turn is taken?

        // Hint: iterate over gameHistory using a loop

        String firstPlayer = playerNames[0];
        String secondPlayer = playerNames[1];

        System.out.println(firstPlayer + " vs. " + secondPlayer);

        for (int i = 0; i < gameHistory.size(); i++) {
            char[][] currentState = gameHistory.get(i);
            if (i % 2 == 0) {
                System.out.println(firstPlayer + ":");
            } else {
                System.out.println(secondPlayer + ":");
            }
            System.out.println(displayGameFromState(currentState));
        }
    }
}
