import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TicTacToe extends Window {

    private boolean state = true; // state of the game
    private String playerChar;
    private Player turn = Player.PLAYERX;
    private boolean aiState = false;
    private AI ai;


    public String getPlayerChar() {
        return playerChar;
    }


    /** Create a working TicTacToe game*/
    public TicTacToe(String title) throws HeadlessException {
        super(title);

        mainText.setText(turn.getAbbreviation() + " Turns");

        // this is needed for the keyEvent
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyHandler(e);
            }
        });
    }



   /** Checking if someone won. The first index return 1 or 0 to check the win and then the next 3
    * values are the winning combination  */
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

    /** Set the state of the game*/
    public void setState(boolean state) {
        this.state = state;
    }

    /** Set the turn to a Player*/
    public void setTurn(Player turn) {
        this.turn = turn;
    }

    /** Returns the state of the game*/
    public boolean isState() {
        return state;
    }

    /** Returns Player's turn*/

    public Player getTurn() {
        return turn;
    }


    /** Actions to do when the button is pressed */
    @Override
    protected void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        // while the game is running
        if (state) {
                // if playerX played  an empty button then make the move
            if (turn == Player.PLAYERX && button.getText().equals("")) {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setFont(new Font("duran", Font.PLAIN, 36));
                button.setText("X");



                // checking if X won
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
                        state = false;
                        popupReset();
                    }else {
                        // if we play against the AI
                        if (aiState) {
                            turn = Player.AI;
                            ai.aiMove();
                        }
                        else
                            turn = Player.PLAYERO;
                        if (state)
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
                        state = false;
                    }

                        // here we use the AI so it's AI turn
                        if (aiState) {
                            turn = Player.AI;
                            ai.aiMove();
                        } else
                            turn = Player.PLAYERX;
                        if (state)
                            mainText.setText(turn.getAbbreviation() + " Turns");
                }
            }
        }
    }



    /** Popup that ask for restarting the game*/
    public void popupReset() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Restart?", "END OF GAME", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            reset();
    }
    /** Popup that ask if the player wants to play "X" or "O" */
    public void playerChoice() {
        String[] options = new String[2];
        options[0] = "X";
        options[1] = "O";
        int answer = JOptionPane.showOptionDialog(this,
                "Do you want to be X or O ",
                "Player", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                options, null);


        if (answer == JOptionPane.YES_OPTION)
            playerChar = "X";
        else
            playerChar = "O";
    }


    /** Restart the game*/
    private void reset() {

        // reset the buttons
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(null);
        }
        // If the AI is playing, set his turn corresponding to the player's char
        if (aiState){
            if (playerChar.equals("X"))
                turn = Player.PLAYERX;
            else
                turn = Player.AI;
        }else
            turn = Player.PLAYERX;

        state = true;
        mainText.setText(turn.getAbbreviation() + " Turns");

        if (turn == Player.AI)
            ai.aiMove();
    }


    /** Handle the keyboard input, currently supporting "R" to restart and "A" to toggle the AI*/
    private void keyHandler(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R) {
            reset();
        }

        // Toggle AI
        if(key == KeyEvent.VK_A) {
            aiState = !aiState;
            if (aiState) {
                playerChoice();
                if (playerChar.equals("X"))
                    Player.AI.setAbbreviation("O");
                else {
                    Player.AI.setAbbreviation("X");
                    turn = Player.AI;
                }
                ai = new AI(this);
            }
            reset();
        }
    }

    /** retuning if it's tie or not*/
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





