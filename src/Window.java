import javax.swing.*;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

    JButton[] buttons = new JButton[9];
    JPanel mainPanel = new JPanel();
    JLabel mainText;
    protected Font duran;

    public Window(String title) throws HeadlessException {


        // Setting up the window
        super(title);

        setSize(450,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        mainPanel.setLayout(new GridLayout(3,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Setting Nimbus LookAndFeel
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
            unsupportedLookAndFeelException.printStackTrace();
           }






        // creating buttons
        for (int i = 0; i < 9; i++) {
                buttons[i] = new JButton("");
                buttons[i].setFocusable(false); // making sure they can't be focused (like with tab)
                buttons[i].addActionListener(this::actionPerformed); // adding an action listener for when we press it
                mainPanel.add(buttons[i]); // adding buttons to our mainPanel 3 by 3 grid


        }
        add(mainPanel,BorderLayout.CENTER); // adding the mainPanel to the center of our window

        mainText = new JLabel("", SwingConstants.CENTER); // empty label for information
        mainText.setFont(new Font("duran", Font.PLAIN, 16));
        mainText.setPreferredSize(new Dimension(0,25));
        mainText.setForeground(new Color(0, 158, 143));
        add(mainText, BorderLayout.NORTH);

        setVisible(true);
        JOptionPane.showMessageDialog(this,"press R to restart");



    }


    protected void font(){
        // creating new font

        try {
            duran = Font.createFont(Font.TRUETYPE_FONT, new File("FontsFree-Net-Duran-Light.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("FontsFree-Net-Duran-Light.ttf")));

        }catch (IOException | FontFormatException e){
            e.printStackTrace();

        }
    }
    protected void actionPerformed(ActionEvent e){

    }

}
