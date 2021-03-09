import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TicTacToe extends Window {

    private boolean state = true; // state of the game
    private Player turn = Player.AI;
    private int nb; // testing var don't consider it


    public TicTacToe(String title) throws HeadlessException {
        super(title);


        mainText.setText(turn.getAbbreviation() + " Turns");
        this.setFocusable(true);

        // KeyListener for restart with "R"
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyHandler(e);
            }
        });
        // first move of the AI
        if (turn == Player.AI)
            aiMove();

    }



    // Method to know if someone won
    // making an array to know which combination won (index from 1 to 3)
    // The index 0 shows us if it's won or not (0 or 1)
    // we compare the text to know if it's equal and we are checking if it's different from blank
    public int[] win() {
        if (buttons[0].getText().equals(buttons[1].getText()) && buttons[2].getText().equals(buttons[0].getText())
                && !buttons[0].getText().equals(""))
            return new int[]{1, 0, 1, 2};
        else if (buttons[3].getText().equals(buttons[4].getText()) && buttons[5].getText().equals(buttons[3].getText()) &&
                !buttons[3].getText().equals(""))
            return new int[]{1, 3, 4, 5};
        else if (buttons[6].getText().equals(buttons[7].getText()) && buttons[8].getText().equals(buttons[6].getText()) &&
                !buttons[6].getText().equals(""))
            return new int[]{1, 6, 7, 8};
        else if (buttons[0].getText().equals(buttons[3].getText()) && buttons[6].getText().equals(buttons[0].getText()) &&
                !buttons[0].getText().equals(""))
            return new int[]{1, 0, 3, 6};
        else if (buttons[1].getText().equals(buttons[4].getText()) && buttons[7].getText().equals(buttons[1].getText()) &&
                !buttons[1].getText().equals(""))
            return new int[]{1, 1, 4, 7};
        else if (buttons[2].getText().equals(buttons[5].getText()) && buttons[8].getText().equals(buttons[2].getText()) &&
                !buttons[2].getText().equals(""))
            return new int[]{1, 2, 5, 8};
        else if (buttons[0].getText().equals(buttons[4].getText()) && buttons[8].getText().equals(buttons[0].getText()) &&
                !buttons[0].getText().equals(""))
            return new int[]{1, 0, 4, 8};
        else if (buttons[2].getText().equals(buttons[4].getText()) && buttons[6].getText().equals(buttons[2].getText()) &&
                !buttons[2].getText().equals(""))
            return new int[]{1, 2, 4, 6};
        return new int[]{0};
    }

    @Override
    protected void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        // if the game is still running
        if (state) {
                // playerX playing on an empty button
            if (turn == Player.PLAYERX && button.getText().equals("")) {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setFont(new Font("duran", Font.PLAIN, 36));
                button.setText("X");



                // checking if the player won
                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};
                    mainText.setText("X won");
                    state = false;
                    // setting win combination to green
                    for (int po : pos)
                        buttons[po].setBackground(new Color(18, 222, 0, 171));

                    popupReset();
                }
                // otherwise we go onto the next player after checking the tie
                else {
                    if (tie()) {
                        mainText.setText("DRAW");
                        popupReset();
                        state = false;

                    turn = Player.PLAYERO;
                    mainText.setText(turn.getAbbreviation() + " Turns");


                    }
                }

            }
            // same for the O Player
            else if (turn == Player.PLAYERO && button.getText().equals("")) {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setText("O");


                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};

                    mainText.setText("O won");
                    state = false;



                    for (int po : pos)
                        buttons[po].setBackground(new Color(18, 222, 0, 171));

                    popupReset();
                } else {
                    if (tie()) {
                        mainText.setText("DRAW");
                        popupReset();
                        state = false;}

                    // here we use the AI so it's AI turn
                    turn = Player.AI;
                    mainText.setText(turn.getAbbreviation() + " Turns");
                    aiMove();



                }
            }
        }
    }


    // simple popup after the game ended to ask if we want to restart or not
    private void popupReset() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Restart?", "END OF GAME", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            resetAI();
    }


    // reset the board
    private void reset() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(null);
        }
        turn = Player.PLAYERX;
        mainText.setText(turn.getAbbreviation() + " Turns");

        state = true;
    }

    //reset the board with the AI implementation
    private void resetAI(){
        reset();
        turn = Player.AI;
        mainText.setText(turn.getAbbreviation() + " Turns");
        aiMove();
    }


    // keyHandler to restart with "R" button of keyboard
    private void keyHandler(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R) {
            resetAI();
        }
    }


    // boolean checking tie
    private boolean tie(){
        int emptyCase = 0;
        if (win()[0] == 0) {
            for (int i = 0; i < 9; i++) {
                if (!buttons[i].getText().equals(""))
                    emptyCase += 1;

            }
        }
        return emptyCase == 9;
    }



    // Value of minimax (10 if win, 0 for tie and -10 if lose)
    private int aiValue() {
        if (win()[0] == 1 && turn == Player.AI)
            return 10;
        else if (win()[0] == 1 && turn == Player.PLAYERO)
            return -10;
        else if (tie())
            return 0;

        return -1;
    }



    private void aiMove() {

        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;

        // going through all of the buttons
        for (int i = 0; i < 9; i++) {


            // if the button is empty
            if (buttons[i].getText().equals("")) {
                // setText to current player's turn so here "X"
                buttons[i].setText(turn.getAbbreviation());
                // go to the next player
                turn = Player.PLAYERO;
                // looking for minimax return
                int score = minimax(0, false);
                turn = Player.AI;
                // cancelling the previous move to make an empty board
                buttons[i].setText("");
                // for each move checking if it's better
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        // making the bestMove found
        buttons[bestMove].setText(Player.AI.getAbbreviation());
        // checking for the win
        if (win()[0] == 1) {
            int[] pos = new int[]{win()[1], win()[2], win()[3]};
            mainText.setText("AI won");
            state = false;
            for (int po : pos)
                buttons[po].setBackground(new Color(18, 222, 0, 171));
            popupReset();
        } else {
            // checking the tie, if false then continue the game
            if (tie()){
                mainText.setText("Tie");
                popupReset();
            }
            turn = Player.PLAYERO;
            mainText.setText(turn.getAbbreviation() + " Turns");

        }
    }

    private int minimax(int depth, boolean maximizingPlayer) {


        // Condition to stop the recursion (if someone won or if it's tie
        if (win()[0] == 1 || tie()) {
            if (aiValue() == 10) {
                nb += 1;
                System.out.println(nb);
            }
            return aiValue();
        }




        // maximizing player, here we just use the minimax algorithm
        if (maximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {

                if (buttons[i].getText().equals("")) {
                    turn = Player.AI;
                    buttons[i].setText(Player.AI.getAbbreviation());

                    int score = minimax(depth + 1, false);
                    buttons[i].setText("");
                    maxScore = Math.max(maxScore, score);

                }

            }
            return maxScore;

        }
        // minimizing player
        else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {


                if (buttons[i].getText().equals("")) {
                    turn = Player.PLAYERO;
                    buttons[i].setText(Player.PLAYERO.getAbbreviation());

                    int score = minimax(depth + 1, true);
                    buttons[i].setText("");


                    minScore = Math.min(minScore, score);

                }
            }
            return minScore;
        }
    }
}





