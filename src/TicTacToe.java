import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class TicTacToe extends Window  {

    private boolean state = true;
    private int draw = 0;
    private Player turn = Player.PLAYERX;



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

    }




    public int[] win(){
        if(buttons[0].getText() == buttons[1].getText() && buttons[2].getText() == buttons[0].getText()
                && buttons[0].getText() != "" )
            return new int[]{1,0,1,2};
        else if(buttons[3].getText() == buttons[4].getText() && buttons[5].getText() == buttons[3].getText() &&
                buttons[3].getText() != "")
            return new int[]{1,3,4,5};
        else if(buttons[6].getText() == buttons[7].getText() && buttons[8].getText() == buttons[6].getText() &&
                buttons[6].getText() != "")
            return new int[]{1,6,7,8};
        else if(buttons[0].getText() == buttons[3].getText() && buttons[6].getText() == buttons[0].getText() &&
                buttons[0].getText() != "")
            return new int[]{1,0,3,6};
        else if(buttons[1].getText() == buttons[4].getText() && buttons[7].getText() == buttons[1].getText() &&
                buttons[1].getText() != "")
            return new int[]{1,1,4,7};
        else if(buttons[2].getText() == buttons[5].getText() && buttons[8].getText() == buttons[2].getText() &&
                buttons[2].getText() != "")
            return new int[]{1,2,5,8};
        else if(buttons[0].getText() == buttons[4].getText() && buttons[8].getText() == buttons[0].getText() &&
                buttons[0].getText() != "")
            return new int[]{1,0,4,8};
        else if(buttons[2].getText() == buttons[4].getText() && buttons[6].getText() == buttons[2].getText() &&
                buttons[2].getText() != "")
            return new int[]{1,2,4,6};
        return new int[] {0};
    }
    @Override
    protected void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (state && draw != 9) {
            if (turn == Player.PLAYERX && button.getText() == "") {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setText("X");
                button.setFont(new Font("duran", Font.PLAIN, 36));
                draw += 1;
                if (win()[0] == 1) {
                    int[] pos = new int[] {win()[1], win()[2], win()[3]};
                    mainText.setText("X won");
                    state = false;
                    for (int i = 0; i < pos.length; i++) {
                        buttons[pos[i]].setBackground(new Color(18, 222, 0, 171));
                    }
                    popupReset();

                } else{
                    turn = Player.PLAYERO;
                    mainText.setText(turn.getAbbreviation() + " Turns");
                    if (draw == 9){
                        mainText.setText("DRAW");
                        popupReset();
                    }

                }
            } else if (turn == Player.PLAYERO && button.getText() == "") {
                button.setForeground(new Color(17, 19, 92, 255));
                button.setText("O");
                draw+= 1;
                button.setFont(new Font("duran", Font.PLAIN, 36));
                if (win()[0] == 1) {
                    int[] pos = new int[] {win()[1], win()[2], win()[3]};
                    mainText.setText("O won");
                    state = false;
                    for (int i = 0; i < pos.length; i++) {
                        buttons[pos[i]].setBackground(new Color(18, 222, 0, 171));
                    }
                    popupReset();

                } else {
                    turn = Player.PLAYERX;
                    mainText.setText(turn.getAbbreviation() + " Turns");
                    if (draw == 9){
                        mainText.setText("DRAW");
                        popupReset();
                    }
                }
            }
        }

    }

    private void popupReset(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null,
                "Restart?","END OF GAME",dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            reset();
    }

    private void reset(){
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(null);
        }
        turn = Player.PLAYERX;
        mainText.setText(turn.getAbbreviation() + " Turns");
        draw = 0;
        state = true;
    }

    private void keyHandler(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_R){
                reset();

            }
    }




}


