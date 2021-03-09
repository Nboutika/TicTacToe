import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TicTacToe extends Window {

    private boolean state = true;
    private Player turn = Player.AI;
    private int nb;


    public TicTacToe(String title) throws HeadlessException {
        super(title);

        mainText.setText(turn.getAbbreviation() + " Turns");
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyHandler(e);
            }
        });
        if (turn == Player.AI)
            aiMove();

    }


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


        if (state) {
            if (turn == Player.PLAYERX && button.getText().equals("")) {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setFont(new Font("duran", Font.PLAIN, 36));
                button.setText("X");


                if (win()[0] == 1) {
                    int[] pos = new int[]{win()[1], win()[2], win()[3]};
                    mainText.setText("X won");
                    state = false;
                    for (int po : pos)
                        buttons[po].setBackground(new Color(18, 222, 0, 171));

                    popupReset();
                } else {
                    turn = Player.PLAYERO;
                    mainText.setText(turn.getAbbreviation() + " Turns");

                    if (tie()) {
                        mainText.setText("DRAW");
                        popupReset();
                        state = false;
                    }
                }

            } else if (turn == Player.PLAYERO && button.getText().equals("")) {
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
                    turn = Player.AI;
                    mainText.setText(turn.getAbbreviation() + " Turns");
                    aiMove();

                    if (tie()) {
                        mainText.setText("DRAW");
                        popupReset();
                        state = false;
                    }
                }
            }
        }
    }

    private void popupReset() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Restart?", "END OF GAME", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            resetAI();
    }

    private void reset() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(null);
        }
        turn = Player.PLAYERX;
        mainText.setText(turn.getAbbreviation() + " Turns");

        state = true;
    }
    private void resetAI(){
        reset();
        turn = Player.AI;
        mainText.setText(turn.getAbbreviation() + " Turns");
        aiMove();
    }

    private void keyHandler(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_R) {
            resetAI();
        }
    }

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
        for (int i = 0; i < 9; i++) {

            if (buttons[i].getText().equals("")) {
                buttons[i].setText(turn.getAbbreviation());
                turn = Player.PLAYERO;
                int score = minimax(0, false);
                turn = Player.AI;
                buttons[i].setText("");
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        buttons[bestMove].setText(Player.AI.getAbbreviation());
        if (win()[0] == 1) {
            int[] pos = new int[]{win()[1], win()[2], win()[3]};
            mainText.setText("AI won");
            state = false;
            for (int po : pos)
                buttons[po].setBackground(new Color(18, 222, 0, 171));
            popupReset();
        } else {
            if (tie()){
                mainText.setText("Tie");
                popupReset();
            }
            turn = Player.PLAYERO;
            mainText.setText(turn.getAbbreviation() + " Turns");

        }
    }

    private int minimax(int depth, boolean maximizingPlayer) {

        if (win()[0] == 1 || tie()) {
            if (aiValue() == 10) {
                nb += 1;
                System.out.println(nb);
            }
            return aiValue();
        }




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

        } else {
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





