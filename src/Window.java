import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    JButton[] buttons = new JButton[9];
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
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

            }
           */
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
            unsupportedLookAndFeelException.printStackTrace();
           }


        for (int i = 0; i < 9; i++) {
                buttons[i] = new JButton("");
                buttons[i].setFocusable(false);
                buttons[i].addActionListener(this::actionPerformed);
                mainPanel.add(buttons[i]);


        }
        add(mainPanel,BorderLayout.CENTER);

        test = new JLabel("Ceci est un message test", SwingConstants.CENTER);
        add(test, BorderLayout.NORTH);

        setVisible(true);

    }

    private void actionPerformed(ActionEvent e){

           JButton button = (JButton) e.getSource();

           button.setForeground(new Color(69,73,85));
           button.setBackground(new Color(114,176,29, 179));
           button.setText("Test");




    }

}
