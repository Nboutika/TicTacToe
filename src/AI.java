import java.awt.*;

public class AI {
    private TicTacToe game;
    private Player opponent;



    /** Create an AI for a TicTacToe game with the player's char as opponent*/
    public AI(TicTacToe game){
        this.game = game;
        if (game.getPlayerChar().equals("X"))
            opponent = Player.PLAYERX;
        else
            opponent = Player.PLAYERO;
    }

    /** Returns the value for minimax (10 for winning, -10 for losing, 0 for tie)*/
    private int aiValue() {
        if (game.win()[0] == 1 && game.getTurn() == Player.AI)
            return 10;
        else if (game.win()[0] == 1 && game.getTurn() == opponent)
            return -10;
        else if (game.tie())
            return 0;

        return -1;
    }


    /** Makes the Ai move using minimax algorithm (need to be AI's turn)*/
    public void aiMove() {

        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;

        // going through all of the buttons
        for (int i = 0; i < 9; i++) {


            // if the button is empty
            if (game.buttons[i].getText().equals("")) {
                // Make the move
                game.buttons[i].setText(game.getTurn().getAbbreviation());
                int score = minimax(0, false);
                game.setTurn(Player.AI);

                // cancelling the previous move to make an empty board
                game.buttons[i].setText("");

                // for each move checking if it's the best move
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        // making the bestMove found
        game.buttons[bestMove].setText(Player.AI.getAbbreviation());

        // checking for the win
        if (game.win()[0] == 1) {
            int[] pos = new int[]{game.win()[1], game.win()[2], game.win()[3]};

            game.mainText.setText("AI won");
            game.setState(false);

            // setting the wining combination to green
            for (int po : pos)
                game.buttons[po].setBackground(new Color(18, 222, 0, 171));
            game.popupReset();
        }
        else{
            // checking the tie, if false then continue the game
            if (game.tie()){
                game.mainText.setText("TIE");
                game.setState(false);
                game.popupReset();
            }
            else {
                game.setTurn(opponent);
                game.mainText.setText(game.getTurn().getAbbreviation() + " Turns");
            }
        }
    }


    /** minimax algorithm*/
    private int minimax(int depth, boolean maximizingPlayer) {


        // Condition to stop the recursion (if someone won or if it's tie
        if (game.win()[0] == 1 || game.tie()) {
            return aiValue();
        }




        // maximizing player, here we just use the minimax algorithm
        if (maximizingPlayer) {
            // maximizing player so the maxScore start at the lowest value
            int maxScore = Integer.MIN_VALUE;

            // going through all the buttons
            for (int i = 0; i < 9; i++) {


                //checking if the button is empty
                if (game.buttons[i].getText().equals("")) {
                    // if so we set the turn to AI's turn and set this button to AI's value
                    game.setTurn(Player.AI);
                    game.buttons[i].setText(game.getTurn().getAbbreviation());

                    // then we make a recursive call to minimax
                    int score = minimax(depth + 1, false);
                    // after going through everything we put back the button to reset the board
                    game.buttons[i].setText("");
                    // and we get the maximum score
                    maxScore = Math.max(maxScore, score);

                }

            }
            // we return the maxScore
            return maxScore;

        }
        // minimizing player same as maximizing except that we are trying to get the lowest value
        else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {


                if (game.buttons[i].getText().equals("")) {
                    game.setTurn(opponent);
                    game.buttons[i].setText(game.getTurn().getAbbreviation());

                    int score = minimax(depth + 1, true);
                    game.buttons[i].setText("");


                    minScore = Math.min(minScore, score);

                }
            }
            return minScore;
        }
    }
}
