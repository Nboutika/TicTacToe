import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToe extends Window {


    public TicTacToe(String title) throws HeadlessException {
        super(title);
        System.out.println(buttons[0].getText());

    }

    @Override
    protected void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        button.setForeground(new Color(69,73,85));
        button.setBackground(new Color(114,176,29, 179));
        button.setText("Test");
    }
}
