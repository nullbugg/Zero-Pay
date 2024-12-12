package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homePage extends JFrame {

    // Variables
    private Container container;
    private JLabel Tittle;
    private JButton nextBtn;
    private Cursor cursor;

    // Font
    Font fontOne = new Font("Arial",Font.BOLD,45);
    Font fontTwo = new Font("Thoma",Font.PLAIN,30);
    Font fontThree = new Font("cambria",Font.PLAIN,20);

    public homePage() {

        mainFrame();

        container = this.getContentPane();
        container.setBackground(Color.GRAY);
        container.setLayout(null);


        // TITTLE SECTION
        Tittle = new JLabel();
        Tittle.setText("Welcome to Zero Pay!");
        Tittle.setBounds(110, 140, 550, 50);
        Tittle.setFont(fontOne);
        Tittle.setForeground(Color.BLACK);
        container.add(Tittle);


        // BUTTON SECTION
        nextBtn = new JButton("Next");
        nextBtn.setFont(fontTwo);
        nextBtn.setBounds(280,250,100,60);
        nextBtn.setCursor(cursor);
        container.add(nextBtn);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login userlogin = new Login();
                userlogin.setVisible(true);
            }
        });
    }

    public void mainFrame()
    {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(600,500);
        setBounds(500,300,700,500);
        setTitle("Home Page");
    }

    public static void main(String[] args) {
        homePage frame = new homePage();
        frame.setVisible(true);
    }
}
