import java.util.Scanner;

public class Main {

    private static void printWinner(int result) {
        //print text based on game winner

        if (result == -1) {
            System.out.println("You win!");
        } else if (result == 1) {
            System.out.println("AI Agent wins!");
        } else {
            System.out.println("Game ended as a DRAW");
        }
    }

    private static int askForDepth(Scanner scanner) {
        //ask user for used depth in minimax algorithm

        int d;
        while (true) {
            System.out.print("Enter maximum search depth: ");
            if (scanner.hasNextInt()) {      
                d = scanner.nextInt();  
                break;                       
            } else {
                System.out.println("That is not an integer. Try again.");
                scanner.next();         
            }
        }
        return d;
    }

    private static int askForTurn(Scanner scanner) {

        //ask player if they want to play first or second

        int turn;
        while (true) {
            System.out.print("Would you like to play first (enter 1) or second(enter 2)? : ");
            if (scanner.hasNextInt()) {      
                turn = scanner.nextInt();
                if (turn == 1 || turn == 2 || turn == 123321) { 
                    break; }  
            } else {
                System.out.println("Enter 1 or 2. Try again.");
                scanner.next();              
            }
        }
        if (turn == 2) {
            System.out.println("You are playing as white (W). The AI Agent is black (B).");
        } else  if (turn == 1) {
        System.out.println("You are playing as black (B). The AI Agent is white (W).");
        }

        System.out.print("\n \n");

        return turn;
    }

    private static Position askPlayerMove(Scanner scanner, Reversi game) {
        //ask player for next move and make sure it is valid 
        
        int row , col;
        while (true) {
            System.out.print("Enter your move ROW : ");
            String moveR = scanner.next();
            System.out.print("Enter your move COLUMN : ");
            String moveC = scanner.next();
            if (!moveR.matches("[1-8]")) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
            if (!moveC.matches("[A-Ha-h]")) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
            row = Integer.parseInt(moveR) - 1;
            col = Character.toUpperCase(moveC.charAt(0)) - 'A';
            Position playerNextPosition = new Position(row, col);
            if (!game.isValidMove(game.getGameBoard(), playerNextPosition, game.getPlayerSymbol()).isEmpty()) {
                return playerNextPosition;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        } 

    }

    private static void aiTestMode(Scanner scanner) {
        //ai vs ai testing mode with different depths

        System.out.println("ai testing mode : \n\n");
        int d1 = askForDepth(scanner);
        int d2 = askForDepth(scanner);
        AiPlayer ai1 = new AiPlayer(d1);
        AiPlayer ai2 = new AiPlayer(d2);
        Reversi testGame = new Reversi(1);
        char ai1Symbol = 'B';
        char ai2Symbol = 'W';
        while (testGame.isNotFinished()){
            if (testGame.hasValidMoves( testGame.getGameBoard() , ai1Symbol ) ) {
                Position ai1NextPosition = ai1.alphaBetaMin(testGame , ai1Symbol);
                testGame.play(ai1NextPosition , ai1Symbol);
            }
            if (testGame.hasValidMoves( testGame.getGameBoard() , ai2Symbol ) ) {
                Position ai2NextPosition = ai2.alphaBetaMax(testGame , ai2Symbol);
                testGame.play(ai2NextPosition , ai2Symbol);
            }
        }
        int result = testGame.winner();
        if (result == -1) {
            System.out.println("Ai Player 1 (depth " + d1 + ") wins!");
        } else if (result == 1) {
            System.out.println("Ai Player 2 (depth " + d2 + ") wins!");
        } else {
            System.out.println("Game ended as a DRAW");
        }
        return;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ask user for depth
        int d = askForDepth(scanner);

        //ask user to play first or second
        int turn = askForTurn(scanner);

        // testing mode ai vs ai
       if (turn == 123321) {
            aiTestMode(scanner);
            return;
        }

        //create a game 
        Reversi game = new Reversi(turn);


        //create ai agent with chosen depth
        AiPlayer ai = new AiPlayer(d);
        

        //print initial game board
        game.PrintGameBoard();

        Position playerNextPosition;

        //play until game ends
        while (game.isNotFinished()){
            if (turn == 1) {
                //players turn
                
                //checks if player has valid moves
                if ( !game.hasValidMoves( game.getGameBoard() , game.getPlayerSymbol() ) ) {
                    System.out.println("No valid moves available for you. Ai plays again.");
                } else {
                    //player has valid moves
                    playerNextPosition = askPlayerMove(scanner , game);
                    game.play(playerNextPosition , game.getPlayerSymbol());
                } 
                turn++;
            } else {
                //ai agents turn

                //checks if ai has valid moves
                if (game.hasValidMoves(game.getGameBoard() , game.getAiSymbol() )) {
                    Position aiNextPosition = ai.alphaBetaMax(game, game.getAiSymbol());
                    game.play(aiNextPosition , game.getAiSymbol());
                    System.out.println("\nAi played at ROW: " + (aiNextPosition.getRow() + 1) + " COLUMN: " + (char)(aiNextPosition.getCol() + (int)'A') + "\n");
                } else {
                    System.out.println("No valid moves available for Ai. You play again.");
                }

                turn--;
            }
            game.PrintGameBoard();
        }

        printWinner(game.winner());

        scanner.close();
    }
}
