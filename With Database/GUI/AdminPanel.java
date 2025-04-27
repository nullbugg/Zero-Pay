package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class AdminPanel extends JFrame {

    private JLabel accountHolderName,copyRight;
    private JButton customer,employee,logOut,Branch;
    private Cursor cursor;

    String username = AdminLogin.username;
    String name = "",password = "";


    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public AdminPanel() {

        mainFrame();

        // Database Connection
        String url = "jdbc:mysql://localhost:3306/Zero_Pay";

        // Variables
        Container container = this.getContentPane();
        container.setBackground(Color.LIGHT_GRAY);
        container.setLayout(null);

        cursor = new Cursor(Cursor.HAND_CURSOR);

        // Database Read
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","");

            Statement statement = connection.createStatement();
            ResultSet DisplayName = statement.executeQuery("select username from Admin_Info where Username = '"+username+"' ");

            while (DisplayName.next()) {
                name = DisplayName.getString(1);
            }
            connection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        // ACCOUNT HOLDER NAME
        accountHolderName = new JLabel();
        accountHolderName.setText("Welcome, " + name);
        accountHolderName.setFont(fontThree);
        accountHolderName.setBounds(20, 20, 300, 20);
        container.add(accountHolderName);

        // LOGOUT BUTTON
        logOut = new JButton("Log Out");
        logOut.setBounds(330, 10, 70, 33);
        container.add(logOut);
        logOut.setCursor(cursor);

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminLogin adminLogin = new AdminLogin();
                adminLogin.setVisible(true);
            }
        });


        // BRANCH OPTION
        Branch = new JButton("Branch");
        Branch.setBounds(3, 50, 200, 120);
        container.add(Branch);
        Branch.setFont(fontThree);
        Branch.setCursor(cursor);
        Branch.setForeground(new Color(1, 1, 27));

        Branch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Branch branch = new Branch();
                branch.setVisible(true);

            }
        });




        // EMPLOYEE OPTION
        employee = new JButton("Employee");
        employee.setBounds(203, 50, 200, 120);
        container.add(employee);
        employee.setFont(fontThree);
        employee.setCursor(cursor);

        employee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                Employee employee = new Employee();
                employee.setVisible(true);

            }
        });


        // CUSTOMER OPTION
        customer = new JButton("Customer");
        customer.setBounds(3, 170, 200, 120);
        container.add(customer);
        customer.setFont(fontThree);
        customer.setCursor(cursor);

        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Customer customer = new Customer();
                customer.setVisible(true);
            }
        });


        // CHANGE PASSWORD OPTION
        customer = new JButton("Change Password");
        customer.setBounds(203, 170, 200, 120);
        container.add(customer);
        customer.setFont(fontThree);
        customer.setCursor(cursor);

        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = JOptionPane.showInputDialog("Username");

                boolean flag = false;
                if (name.equals(username))
                {
                    flag = true;
                }

                if (flag)
                {
                    password = JOptionPane.showInputDialog("New Password");
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        PreparedStatement senderUpdate = connection.prepareStatement("UPDATE Admin_Info SET Password = '"+password+"' WHERE Username = '"+username+"' ");
                        senderUpdate.executeUpdate();
                        JOptionPane.showMessageDialog(null,"New Password Updated");

                        connection.close();
                    }
                    catch (Exception exception)
                    {
                        System.out.println(exception);
                    }


                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Username!", "Warring", JOptionPane.WARNING_MESSAGE);
                }




            }
        });


        // COPY RIGHT MESSAGE
        copyRight = new JLabel();
        copyRight.setText("Copy Right - Zero Pay!");
        copyRight.setFont(fontTwo);
        copyRight.setBounds(130, 320, 300, 20);
        container.add(copyRight);

//        JOptionPane.showMessageDialog(null,"Login Successful");
    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setBounds(630, 300, 405, 400);
        setTitle("Admin Panel!");
    }

    public static void main(String[] args) {
        UserAccount user = new UserAccount();
        user.setVisible(true);
    }
}