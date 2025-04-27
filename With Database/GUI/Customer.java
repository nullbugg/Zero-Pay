package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Customer extends JFrame {

    private JLabel copyRight;
    private JButton logOut,SearchEmployee,changeCustomer;
    private Cursor cursor;

    String username = AdminLogin.username;
    String customerName = "", customerPhone ="",branchName = "",customerID = "",newPhone = "";
    String all_ID = "";
    String name = "";
    double balance;




    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public Customer() {

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

        // CLOSE BUTTON
        logOut = new JButton("Close");
        logOut.setBounds(330, 10, 70, 33);
        container.add(logOut);
        logOut.setCursor(cursor);

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminPanel adminpanel = new AdminPanel();
                adminpanel.setVisible(true);
            }
        });


        //=====================================================================================
        //                          CUSTOMER DETAILS OPTION
        //=====================================================================================

        SearchEmployee = new JButton("Customer Details");
        SearchEmployee.setBounds(100, 50, 200, 120);
        container.add(SearchEmployee);
        SearchEmployee.setFont(fontThree);
        SearchEmployee.setCursor(cursor);

        SearchEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerPhone = JOptionPane.showInputDialog("Customer Phone Number");
                boolean flag = false;

                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Phone from Customer_Info where Customer_Info.Phone = '"+customerPhone+"' ");


                    while (Display.next()) {
                        customerID = Display.getString(1);

                        if (customerID.equals(customerPhone))
                        {
                            flag = true;
                        }
                    }
                    connection.close();
                }
                catch (Exception exception)
                {
                    System.out.println(exception);
                }

                if (flag)
                {
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        Statement statement = connection.createStatement();
                        ResultSet Display = statement.executeQuery("select Full_Name,Current_Balance,Branch_Name from Customer_Info join Branch on Customer_Info.Branch_ID = Branch.Branch_ID " +
                                "join Accounts_Balance on Customer_Info.Phone = Accounts_Balance.Phone where Customer_Info.Phone = '"+customerPhone+"' ");


                        while (Display.next()) {
                            customerName = Display.getString(1);
                            balance = Display.getDouble(2);
                            branchName = Display.getString(3);

                        }


                        connection.close();
                    }
                    catch (Exception exception)
                    {
                        System.out.println(exception);
                    }

                    JOptionPane.showMessageDialog(null, "Phone    :  " + customerPhone +"\n" + "Name     :  " + customerName + "\n"  + "Balance  :  " + balance +"\n" + "Branch   : " + branchName);

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Customer Phone Number!", "Warring", JOptionPane.WARNING_MESSAGE);
                }




            }
        });

        //=====================================================================================
        //                            CHANGE EMPLOYEE NUMBER OPTION
        //=====================================================================================

        changeCustomer = new JButton("Change Number");
        changeCustomer.setBounds(100, 170, 200, 120);
        container.add(changeCustomer);
        changeCustomer.setFont(fontThree);
        changeCustomer.setCursor(cursor);

        changeCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerPhone = JOptionPane.showInputDialog("Phone Number");
                boolean flag = false;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Phone from Customer_Info  where Phone = '"+customerPhone+"' ");

                    while (Display.next()) {
                         all_ID = Display.getString(1);

                        if (all_ID.equals(customerPhone))
                        {
                            flag = true;
                        }

                    }

                    connection.close();
                }
                catch (Exception exception)
                {
                    System.out.println(exception);
                }

                if (flag)
                {
                    customerName = JOptionPane.showInputDialog("Customer Name");
                    flag = false;

                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        Statement statement = connection.createStatement();
                        ResultSet Display = statement.executeQuery("select Full_Name from Customer_Info  where Phone = '"+customerPhone+"' ");

                        while (Display.next()) {
                            name = Display.getString(1);

                            if (name.equals(customerName))
                            {
                                flag = true;
                            }

                        }

                        connection.close();
                    }
                    catch (Exception exception)
                    {
                        System.out.println(exception);
                    }


                    if (flag)
                    {
                        newPhone = JOptionPane.showInputDialog("New Number");

                        try
                        {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection connection = DriverManager.getConnection(url,"root","");

                            PreparedStatement senderUpdate = connection.prepareStatement("UPDATE Customer_Info SET Phone = '"+newPhone+"' WHERE Full_Name = '"+name+"' ");
                            senderUpdate.executeUpdate();

                            JOptionPane.showMessageDialog(null,"New Number is Updated");

                            connection.close();
                        }
                        catch (Exception exception)
                        {
                            System.out.println(exception);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Name!", "Warring", JOptionPane.WARNING_MESSAGE);
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Number!", "Warring", JOptionPane.WARNING_MESSAGE);
                }


            }
        });


        // COPY RIGHT MESSAGE
        copyRight = new JLabel();
        copyRight.setText("Copy Right - Zero Pay!");
        copyRight.setFont(fontTwo);
        copyRight.setBounds(130, 320, 300, 20);
        container.add(copyRight);

    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setBounds(630, 300, 405, 400);
        setTitle("Customer");
    }

    public static void main(String[] args) {
        UserAccount user = new UserAccount();
        user.setVisible(true);
    }
}