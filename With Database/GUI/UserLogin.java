package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class UserLogin extends JFrame {

    // Variables
    private Container container;
    private JLabel userLabel, passwordLabel,registerMessage;
    private JTextField userTextField;
    private JPasswordField userPassword,passwordfield;
    private JButton login,signUp,closeBtn, nextBtn;
    private Cursor cursor;
    private JCheckBox showPasswordCheckBox;
    public static String userPhone;
    private String password,phone,storedPassword;

    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("cambria", Font.BOLD, 20);
    Font fontThree = new Font("Thoma", Font.PLAIN, 20);

    public UserLogin() {

        mainFrame();

        //CONTAINER
        container = this.getContentPane();
        container.setBackground(Color.LIGHT_GRAY);
        container.setLayout(null);

        // CLOSE BUTTON
        closeBtn = new JButton("Close");
        closeBtn.setBounds(610, 15, 70, 33);
        container.add(closeBtn);
        closeBtn.setCursor(cursor);

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                homePage home = new homePage();
                home.setVisible(true);
            }
        });

        //phone number
        userLabel = new JLabel();
        userLabel.setText("Phone Number");
        userLabel.setFont(fontTwo);
        userLabel.setBounds(213, 40, 250, 30);
        container.add(userLabel);

        userTextField = new JTextField();
        userTextField.setFont(fontTwo);
        userTextField.setBounds(210, 72, 250, 43);
        container.add(userTextField);

        // PASSWORD
        userPassword = new JPasswordField();
        passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        passwordLabel.setFont(fontTwo);
        passwordLabel.setBounds(213, 140, 250, 30);
        container.add(passwordLabel);

        userPassword.setBounds(210, 170, 250, 43);
        userPassword.setFont(fontTwo);
        container.add(userPassword);

        // SHOW PASSWORD CHECKBOX
        showPasswordCheckBox = new JCheckBox("Show password");
        showPasswordCheckBox.setFont(new Font("cambria", Font.BOLD, 12));
        showPasswordCheckBox.setForeground(Color.BLACK);
        showPasswordCheckBox.setBackground(Color.BLACK);
        showPasswordCheckBox.setBackground(new Color(82, 143, 209));
        showPasswordCheckBox.setBounds(210, 220, 150, 20);
        showPasswordCheckBox.setFocusable(false);
        showPasswordCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        container.add(showPasswordCheckBox);

        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    userPassword.setEchoChar((char) 0); // Show the password

                } else {
                    userPassword.setEchoChar('\u2022'); // Hide the password (display as bullet)
                }
            }
        });

        // LOGIN BUTTON
        cursor = new Cursor(Cursor.HAND_CURSOR);
        login = new JButton("Log In");
        login.setBounds(290, 260, 100, 40);
        login.setFont(new Font("cambria", Font.BOLD, 16));
        container.add(login);
        login.setCursor(cursor);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPhone = userTextField.getText();
                password = userPassword.getText();

                boolean phoneFlag = false;
                boolean passwordFlag = false;

                // Code to Read from Database

                String url = "jdbc:mysql://localhost:3306/Zero_Pay";

                try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url,"root","");
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select Phone,Password from Customer_Info where Phone = "+userPhone+" ");

                while (resultSet.next()) {
                    phone = resultSet.getString(1);
                    storedPassword = resultSet.getString(2);
                }
                if (phone.equals(userPhone)){
                    phoneFlag = true;
                }

                if (storedPassword.equals(password)){
                    passwordFlag = true;
                }
                connection.close();

            }
                catch (Exception ex)
            {
                System.out.println(ex);
            }

                if (phoneFlag && passwordFlag ){
                dispose();
                UserAccount user = new UserAccount();
                user.setVisible(true);
            }
                else {
                JOptionPane.showMessageDialog(null, "Invalid Number or Password","Warring",0);
                userTextField.setText("");
                userPassword.setText("");
            }
        }
        });




        // SIGN UP
        registerMessage = new JLabel();
        registerMessage.setText("Don't have an account?");
        registerMessage.setFont(fontTwo);
        registerMessage.setBounds(170, 331, 400, 30);
        container.add(registerMessage);

        signUp = new JButton("Sign Up");
        signUp.setBounds(420, 330, 90, 33);
        signUp.setFont(new Font("cambria", Font.BOLD, 12));
        container.add(signUp);
        signUp.setCursor(cursor);

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText("");
                userPassword.setText("");
                dispose();
                UserSignUP usersignup = new UserSignUP();
                usersignup.setVisible(true);
            }
        });
    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 300, 700, 500);
        setTitle("User Login Page");
    }

    public static void main(String[] args) {
        UserLogin userLogin = new UserLogin();
        userLogin.setVisible(true);
    }
}