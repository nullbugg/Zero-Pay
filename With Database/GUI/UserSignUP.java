package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserSignUP extends JFrame {

    // Variables
    private Container container;
    private JLabel nameLabel, passwordLabel,re_passwordLabel, emailLabel,phoneLabel,branch_hinsLabel;
    private JTextField nameTextField,emailTextField,phoneTextField,branchTextField;
    private JPasswordField userPassword,userRe_Password;
    private JButton register,closeBtn;
    private Cursor cursor;
    private JCheckBox showPasswordCheckBox;
    String storedPhone = "";

    // Font
    Font fontOne = new Font("Thoma", Font.BOLD, 11);
    Font fontTwo = new Font("Thoma", Font.BOLD, 22);
    Font fontThree = new Font("cambria", Font.BOLD, 16);

    public UserSignUP() {

        mainFrame();

        container = this.getContentPane();
        container.setBackground(Color.LIGHT_GRAY);
        container.setLayout(null);

        cursor = new Cursor(Cursor.HAND_CURSOR);

        // CLOSE BUTTON
        closeBtn = new JButton("Close");
        closeBtn.setBounds(610, 15, 70, 33);
        container.add(closeBtn);
        closeBtn.setCursor(cursor);

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserLogin userLogin = new UserLogin();
                userLogin.setVisible(true);
            }
        });

        // NAME LABEL
        nameLabel = new JLabel();
        nameLabel.setText("Full Name          :");
        nameLabel.setFont(fontTwo);
        nameLabel.setBounds(60, 40, 300, 30);
        container.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setFont(fontThree);
        nameTextField.setBounds(285, 40, 300, 40);
        container.add(nameTextField);


        // EMAIL LABEL
        emailLabel = new JLabel();
        emailLabel.setText("E-mail               : ");
        emailLabel.setFont(fontTwo);
        emailLabel.setBounds(60, 95, 300, 30);
        container.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setFont(fontThree);
        emailTextField.setBounds(285, 95, 300, 40);
        container.add(emailTextField);


        // PHONE NUMBER LABEL
        phoneLabel = new JLabel();
        phoneLabel.setText("Phone Number  : ");
        phoneLabel.setFont(fontTwo);
        phoneLabel.setBounds(60, 150, 300, 30);
        container.add(phoneLabel);

        phoneTextField = new JTextField();
        phoneTextField.setFont(fontThree);
        phoneTextField.setBounds(285, 150, 300, 40);
        container.add(phoneTextField);


        // PASSWORD LABEL
        userPassword = new JPasswordField();
        passwordLabel = new JLabel();
        passwordLabel.setText("Password          :");
        passwordLabel.setFont(fontTwo);
        passwordLabel.setBounds(60, 205, 300, 30);
        container.add(passwordLabel);

        userPassword.setBounds(284, 205, 300, 40);
        userPassword.setFont(fontThree);
        container.add(userPassword);


        // RE-PASSWORD LABEL
        userRe_Password = new JPasswordField();
        re_passwordLabel = new JLabel();
        re_passwordLabel.setText("Re-Password    :");
        re_passwordLabel.setFont(fontTwo);
        re_passwordLabel.setBounds(60, 260, 300, 30);
        container.add(re_passwordLabel);

        userRe_Password.setBounds(284, 260, 300, 40);
        userRe_Password.setFont(fontThree);
        container.add(userRe_Password);

        // SHOW & HIDE PASSWORD CHECKBOX
        showPasswordCheckBox = new JCheckBox("Show password");
        showPasswordCheckBox.setFont(new Font("cambria", Font.BOLD, 12));
        showPasswordCheckBox.setForeground(Color.BLACK);
        showPasswordCheckBox.setBackground(Color.BLACK);
        showPasswordCheckBox.setBackground(new Color(82, 143, 209));
        showPasswordCheckBox.setBounds(457, 295, 200, 40);
        showPasswordCheckBox.setFocusable(false);
        showPasswordCheckBox.setCursor(cursor);
        container.add(showPasswordCheckBox);

        showPasswordCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    userPassword.setEchoChar((char) 0);
                    userRe_Password.setEchoChar((char) 0); // Show the password

                } else {
                    userPassword.setEchoChar('\u2022');
                    userRe_Password.setEchoChar('\u2022'); // Hide the password (display as bullet)
                }
            }
        });


        // Branch LABEL
        branch_hinsLabel = new JLabel();
        branch_hinsLabel.setText("101 - Branch_A     201 - Branch_B     301 - Branch_C");
        branch_hinsLabel.setFont(fontOne);
        branch_hinsLabel.setBounds(285, 330, 400, 30);
        container.add(branch_hinsLabel);

        branch_hinsLabel = new JLabel();
        branch_hinsLabel.setText("Branch ID          : ");
        branch_hinsLabel.setFont(fontTwo);
        branch_hinsLabel.setBounds(60, 360, 300, 30);
        container.add(branch_hinsLabel);

        branchTextField = new JTextField();
        branchTextField.setFont(fontThree);
        branchTextField.setBounds(285, 360, 300, 40);
        container.add(branchTextField);

        // REGISTRATION BUTTON
        register = new JButton("Register");
        register.setBounds(300, 415, 100, 40);
        container.add(register);
        register.setCursor(cursor);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String phone = phoneTextField.getText();
                String password = userPassword.getText();
                String rePassword = userRe_Password.getText();
                String branchid = branchTextField.getText();

                if (password.isEmpty() || rePassword.isEmpty() || phone.isEmpty() || name.isEmpty() || email.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Invalid Input","Warring",0);
                }else if (phone.length() != 11){
                    JOptionPane.showMessageDialog(null, "Phone number must be 11 digit!","Warring",0);
                }
                else if (password.equals(rePassword)){

                    // Code to Insert a Value in Database

                    String url = "jdbc:mysql://localhost:3306/Zero_Pay";
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("select Phone from Customer_Info where Phone = "+phone+" ");

                        while (resultSet.next()) {
                            storedPhone = resultSet.getString(1);
                        }

                        if (!(storedPhone.equals(phone)))
                        {
                            PreparedStatement newAccounts = connection.prepareStatement("INSERT INTO Customer_Info VALUES ('"+phone+"','"+name+"', '"+email+"','"+password+"',"+branchid+")");
                            newAccounts.executeUpdate();

                            double x = 100.00;
                            PreparedStatement accountBonus = connection.prepareStatement("INSERT INTO Accounts_Balance VALUES ('"+phone+"',"+x+",'Bonus from Zero Pay')");
                            accountBonus.executeUpdate();
                            connection.close();

                            JOptionPane.showMessageDialog(null, "Register Successful");
                            dispose();
                            UserLogin userLogin = new UserLogin();
                            userLogin.setVisible(true);
                        }
                        else
                        {
                            connection.close();
                            JOptionPane.showMessageDialog(null, "You already have an account","Warring",0);
                        }

                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Password don't match","Warring",0);
                    userPassword.setText("");
                    userRe_Password.setText("");

                }
            }
        });

    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setBounds(500, 300, 700, 500);
        setTitle("User Sign Up!");
    }

    public static void main(String[] args) {
        UserSignUP userSign = new UserSignUP();
        userSign.setVisible(true);
    }
}