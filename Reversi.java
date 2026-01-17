import java.util.List;
import java.util.ArrayList;

public class Reversi {

    private char[][] gameBoard = new char[8][8];
    private char playerSymbol;
    private char aiSymbol;
    private int playersTurn;

    public Reversi(int playersTurn){
        //Reversi game constructor

        this.playersTurn = playersTurn;
        this.aiSymbol = (playersTurn == 1) ? 'W' : 'B';
        this.playerSymbol = (playersTurn == 1) ? 'B' : 'W';

        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gameBoard[i][j] = '-';
            }
        }
        gameBoard[3][3] = 'W';
        gameBoard[3][4] = 'B';
        gameBoard[4][3] = 'B';
        gameBoard[4][4] = 'W';
    }

    public Reversi(Reversi gameToCopy){
        //Reversi game copy constructor

        this.gameBoard = copyBoard(gameToCopy.getGameBoard());
        this.playerSymbol = gameToCopy.getPlayerSymbol();
        this.aiSymbol = gameToCopy.getAiSymbol();
        this.playersTurn = gameToCopy.getPlayersTurn();

    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public char getAiSymbol() {
        return this.aiSymbol;
    }

    public char getPlayerSymbol() {
        return this.playerSymbol;
    }

    public int getPlayersTurn() {
        return playersTurn;
    }

    private char[][] copyBoard(char[][] board) {
        //copy game board to use in copy constructor

        char[][] newBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    } 

    public void PrintGameBoard() {
        //prints game board

        System.out.println("  A B C D E F G H");
        for (int row = 0; row < 8; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < 8; col++) {
                System.out.print(gameBoard[row][col] + " ");
            }
            System.out.println();
        }
    }

    public boolean isNotFinished() {
        //checks if game is not finished

        if (hasValidMoves(gameBoard, 'B') || hasValidMoves(gameBoard, 'W')) {
            return true;
        }
        return false;
    }

    public int winner() {
        //returns -1 if player wins, 1 if ai wins, 0 if tie
        int playerCount = 0;
        int aiCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] == getPlayerSymbol()) {
                    playerCount++;
                } else if (gameBoard[i][j] == getAiSymbol()) {
                    aiCount++;
                }
            }
        }

        if (playerCount > aiCount) {
            return -1; // player wins
        } else if (aiCount > playerCount) {
            return 1; // AI wins
        }
        return 0; // tie
    }

    public List<Integer> isValidMove(char[][] gameBoard, Position pos, char symbol) {
        //returns a list of directions to where the position captures 
        // 1 is top and numbers continue clockwise

        int row = pos.getRow();
        int col = pos.getCol();
        int c = col;
        int r = row;
        List<Integer> directions = new ArrayList<>();

        if (row < 0 || row >= 8 || col < 0 || col >= 8 || gameBoard[row][col] != '-') {
            return directions;
        }

        char oppenentSymbol;

        if (symbol == 'B') {
            oppenentSymbol = 'W';
        } else {
            oppenentSymbol = 'B';
        }

        //captures from top
        if (row <= 1 || gameBoard[row-1][col] != oppenentSymbol) {
        } else {
            while (row >= 1) {
                row--;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(1);
                    break;
                }
            }
        }
        row = r;

        //captures from top right
        if (row <= 1 || col >= 6 || gameBoard[row-1][col+1] != oppenentSymbol) {
        } else {
            while (row >= 1 && col <= 6) {
                row--;
                col++;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(2);
                    break;
                }
            }
        }
        row = r;
        col = c;

        //captures from right
        if (col >= 6 || gameBoard[row][col+1] != oppenentSymbol) {
        } else {
            while (col <= 6) {
                col++;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(3);
                    break;
                }
            }
        }
        col = c;

        //captures from bottom right
        if (row >= 6 || col >= 6 || gameBoard[row+1][col+1] != oppenentSymbol) {
        } else {
            while (row <= 6 && col <= 6) {
                row++;
                col++;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(4);
                    break;
                }
            }
        }
        row = r;
        col = c;

        //captures from bottom
        if (row >= 6 || gameBoard[row+1][col] != oppenentSymbol) {
        } else {
            while (row <= 6) {
                row++;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(5);
                    break;
                }
            }
        }
        row = r;

        //captures from bottom left
        if (row >= 6 || col <= 1 || gameBoard[row+1][col-1] != oppenentSymbol) {
        } else {
            while (row <= 6 && col >= 1) {
                row++;
                col--;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(6);
                    break;
                }
            }
        }
        row = r;
        col = c;
    
        //capltures from left
        if (col <= 1 || gameBoard[row][col-1] != oppenentSymbol) {
        } else {
            while (col >= 1) {
                col--;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(7);
                    break;
                }
            }
        }
        col = c;

        //captures from top left
        if (row <= 1 || col <= 1 || gameBoard[row-1][col-1] != oppenentSymbol) {
        } else {
            while (row >= 1 && col >= 1) {
                row--;
                col--;
                if (gameBoard[row][col] == '-') {
                    break;
                }
                if (gameBoard[row][col] == symbol) {
                    directions.add(8);
                    break;
                }
            }
        }
        row = r;
        col = c;

        return directions;
    }

    public boolean hasValidMoves(char[][] gameBoard, char symbol) {
        //checks if a symbol can be played anywhere , player with that symbol can play
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                List<Integer> directions = isValidMove(gameBoard, pos, symbol);
                if (!directions.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void play(Position pos, char symbol) {
        //places a symbol on the board and updates the board 


        int row = pos.getRow();
        int col = pos.getCol();
        int r , c;
        if (row < 0 || row >= 8 || col < 0 || col >= 8 || gameBoard[row][col] != '-') {
            return;
        }
        List<Integer> directions = isValidMove(gameBoard, pos, symbol);
        if (directions.isEmpty()) {
            return;
        }

        while (!directions.isEmpty()) {
            int dir = directions.remove(0);
            r = row;
            c = col;
            if (dir == 1){
                //top
                r--;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r--;
                }
            } else if (dir == 2){
                //top right
                r--;
                c++;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r--;
                    c++;
                }
            } else if (dir == 3){
                //right
                c++;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    c++;
                }
            } else if (dir == 4){
                //bottom right
                r++;
                c++;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r++;
                    c++;
                }
            } else if (dir == 5){
                //bottom
                r++;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r++;
                }
            } else if (dir == 6){
                //bottom left
                r++;
                c--;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r++;
                    c--;
                }
            } else if (dir == 7){
                //left
                c--;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    c--;
                }
            } else if (dir == 8){
                //top left
                r--;
                c--;
                while (gameBoard[r][c] != symbol) {
                    gameBoard[r][c] = symbol;
                    r--;
                    c--;
                }
            }

        }

        gameBoard[row][col] = symbol;
    }


}

