import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TicTacToe extends Window {

    private boolean state = true; // state of the game
    private Player turn = Player.AI;
    private AI ai = new AI(this);



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
            ai.aiMove();

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

    public void setState(boolean state) {
        this.state = state;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public boolean isState() {
        return state;
    }

    public Player getTurn() {
        return turn;
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
                        mainText.setText("TIE");
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
                        mainText.setText("TIE");
                        popupReset();
                        state = false;}

                    // here we use the AI so it's AI turn
                    turn = Player.AI;
                    mainText.setText(turn.getAbbreviation() + " Turns");
                    ai.aiMove();
                }
            }
        }
    }


    // simple popup after the game ended to ask if we want to restart or not
    public void popupReset() {
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
        ai.aiMove();
    }


    // keyHandler to restart with "R" button of keyboard
    private void keyHandler(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R) {
            resetAI();
        }
    }


    // boolean checking tie
    public boolean tie(){
        int emptyCase = 0;
        if (win()[0] == 0) {
            for (int i = 0; i < 9; i++) {
                if (!buttons[i].getText().equals(""))
                    emptyCase += 1;

            }
        }
        return emptyCase == 9;
    }










}





