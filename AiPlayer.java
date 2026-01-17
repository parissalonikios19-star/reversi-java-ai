import java.util.List;
import java.util.ArrayList;

public class AiPlayer {
    
    private int depth;

    public AiPlayer(int depth) {
        //ai agent constructor
        this.depth = depth;
    }

    private List<Position> allPosibleNextPositions(Reversi game , char symbol) {
        //returns all posible next positions for agent

        List<Position> positions = new ArrayList<>();
        char[][] board = game.getGameBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == '-') {
                    Position pos = new Position(i, j);
                    if (!game.isValidMove(board, pos, symbol).isEmpty()) {
                        positions.add(pos);
                    }
                }
            }
        }

        return positions;
    }

    private int boardValue(Reversi game, char aiSymbol){
        //evaluate current board
        //score based on number of pieces , unflippable pieces , possible moves and game winner
        //ai agent is max  
        //player is min

        int aiCount = 0;
        int playerCount = 0;
        int score;
        char playerSymbol = (aiSymbol == 'B') ? 'W' : 'B';
        char[][] board = game.getGameBoard();
        //weights for each score factor
        int [] w = {1 , 25 , 10, 1000};


        //score update on number of pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == aiSymbol) {
                    aiCount++;
                } else if (board[i][j] == playerSymbol) {
                    playerCount++;
                }
            }
        }
        score = w[0] * (aiCount - playerCount);

        //score update on unflippable pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == aiSymbol && isStable(game, i, j)) {
                    score += w[1];
                } else if (board[i][j] == playerSymbol && isStable(game, i, j)) {
                    score -= w[1];
                }
            }
        }

        //score update on possible moves and capturing directions

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                score +=  w[2]*game.isValidMove(board, pos, aiSymbol).size();
                score -=  w[2]*game.isValidMove(board, pos, playerSymbol).size();   
            }
        }

        // score update if there is a winner

        score += w[3] * game.winner();


        return score;
    }

    public char[][] copyBoard(char[][] board) {
        //copy game board to use in minimax algorithm

        char[][] newBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    } 

    public Position alphaBetaMax(Reversi game , char symbol) {
        // minimax algorithm with alpha beta implementation 
        // max plays first

        int v = maxValue(game, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        for (Position position : allPosibleNextPositions(game , symbol)) {
            Reversi newGame = new Reversi(game);
            newGame.play(position , symbol);
            int minV = minValue(newGame , depth - 1 , Integer.MIN_VALUE , Integer.MAX_VALUE);
            if (minV == v) {
                return position;
            }
        }
        return new Position(-1, -1); // ai has no possible moves
        
    }

    public Position alphaBetaMin(Reversi game , char symbol) {
        // minimax algorithm with alpha beta implementation
        // min plays first

        int v = minValue(game, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        for (Position position : allPosibleNextPositions(game , symbol)) {
            Reversi newGame = new Reversi(game);
            newGame.play(position , symbol);
            int maxV = maxValue(newGame , depth - 1 , Integer.MIN_VALUE , Integer.MAX_VALUE);
            if (maxV == v) {
                return position;
            }
        }
        return new Position(-1, -1); // ai has no possible moves
        
    }

    private int maxValue(Reversi game, int depth, int alpha, int beta) {
        // max value used in alpha beta

        char aiSymbol = game.getAiSymbol();
        if (depth == 0 || !game.isNotFinished()) {
            return boardValue(game, aiSymbol);
        }
        
        int v = Integer.MIN_VALUE;

        for (Position position : allPosibleNextPositions(game , aiSymbol)) {
            Reversi newGame = new Reversi(game);
            newGame.play(position , aiSymbol);
            v = Math.max(v , minValue(newGame , depth - 1 , alpha , beta));
            if (v >= beta) {
                return v;
            }
            alpha = Math.max(alpha , v);    

        }
        return v;
    }

    private int minValue(Reversi game, int depth, int alpha, int beta) {
        // min value used in alpha beta

        char playerSymbol = game.getPlayerSymbol();
        char aiSymbol = game.getAiSymbol();

        if (depth == 0 || !game.isNotFinished()) {
            return boardValue(game, aiSymbol);
        }
        
        int v = Integer.MAX_VALUE;
        for (Position position : allPosibleNextPositions(game , playerSymbol)) {
            Reversi newGame = new Reversi(game);
            newGame.play(position , playerSymbol);
            v = Math.min(v , maxValue(newGame , depth - 1 , alpha , beta));
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(beta , v);    

        }
        return v;
    }

    private boolean isStable(Reversi game, int row, int col) {
        //checks if a piece is unflippable

        char[][] board = game.getGameBoard();
        char symbol = board[row][col];

        if (symbol != 'B' && symbol != 'W') return false;

        int[][] directions = {
            {-1, 0}, {1, 0},
            {0, -1}, {0, 1},     
            {-1, -1}, {1, 1},    
            {-1, 1}, {1, -1}     
        };

        // Check pairs of directions
        for (int i = 0; i < 8; i += 2) {
            if (!stableInDirection(board, row, col, symbol, directions[i], directions[i+1])) {
                return false;
            }
        }

        return true;
    }

    private boolean stableInDirection(char[][] board, int row, int col, char symbol, int[] dir1, int[] dir2) {
        //checks if a piece is stable in a pair of directions
        // used in isStable 

        return (checkDirection(board, row, col, symbol, dir1) && checkDirection(board, row, col, symbol, dir2));
    }

    private boolean checkDirection(char[][] board, int row, int col, char symbol, int[] direction) {
        //checks if a piece is stable in a direction
        // used in stableInDirection

        int r = row + direction[0];
        int c = col + direction[1];

        while (r >= 0 && r < 8 && c >= 0 && c < 8) {
            if (board[r][c] != symbol) {
                return false;
            }
            r += direction[0];
            c += direction[1];
        }
        return true;
    }



}
