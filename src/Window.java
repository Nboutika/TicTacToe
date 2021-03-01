import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Window extends JFrame {

    JButton[][] buttons = new JButton[3][3];
    JPanel mainPanel = new JPanel();
    JLabel test;

    public Window(String title) throws HeadlessException {

        super(title);
        setSize(450,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        mainPanel.setLayout(new GridLayout(3,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                buttons[i][j] = new JButton("");
                mainPanel.add(buttons[i][j]);
            }

        }
        add(mainPanel,BorderLayout.CENTER);

        test = new JLabel("Ceci est un message test", SwingConstants.CENTER);
        add(test, BorderLayout.NORTH);

        setVisible(true);

    }

}
