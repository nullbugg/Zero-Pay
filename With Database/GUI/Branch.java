package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Branch extends JFrame {

    private JLabel copyRight;
    private JButton deleteBranch,logOut,Branch,BranchDetails;
    private Cursor cursor;

    String username = AdminLogin.username;
    int branchID = -1;
    String branchName = "",branchAddress="", all_name = "";
    String name = "";
    int all_ID = 0;



    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public Branch() {

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
        //                             ADD BRANCH OPTION
        //=====================================================================================

        Branch = new JButton("Add Branch");
        Branch.setBounds(100, 50, 200, 120);
        container.add(Branch);
        Branch.setFont(fontThree);
        Branch.setCursor(cursor);
        Branch.setForeground(new Color(1, 1, 27));

        Branch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = JOptionPane.showInputDialog("Branch ID");
                branchID = Integer.parseInt(id);

                boolean flag = true;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Branch_ID from Branch where Branch_ID = "+branchID+" ");

                    while (Display.next()) {
                        all_ID = Display.getInt(1);
                        if (all_ID == branchID)
                        {
                            flag = false;
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
                    branchName = JOptionPane.showInputDialog("Branch Name");

                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        Statement statement = connection.createStatement();
                        ResultSet BranchName = statement.executeQuery("select Branch_Name from Branch where Branch_Name = '"+branchName+"' ");

                        while (BranchName.next()) {
                            all_name = BranchName.getString(1);
                            if (all_name.equals(branchName))
                            {
                                flag = false;
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
                        branchAddress = JOptionPane.showInputDialog("Branch Address");

                        if (branchID != -1 && !(branchName.isEmpty()) && !(branchAddress.isEmpty()))
                        {
                            // Database Read
                            try
                            {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url,"root","");

                                PreparedStatement newBranch = connection.prepareStatement("INSERT INTO Branch VALUES ("+branchID+",'"+branchName+"','"+branchAddress+"')");
                                newBranch.executeUpdate();


                                connection.close();
                            }
                            catch (Exception exception)
                            {
                                System.out.println(exception);
                            }
                            JOptionPane.showMessageDialog(null, "Branch Added Successfully!");

                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Branch Name Already Exists!", "Warring", JOptionPane.WARNING_MESSAGE);
                    }


                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Branch ID Already Exists!", "Warring", JOptionPane.WARNING_MESSAGE);
                }



            }
        });


        //=====================================================================================
        //                            BRANCH DETAILS OPTION
        //=====================================================================================

        BranchDetails = new JButton("Search Branch");
        BranchDetails.setBounds(100, 170, 200, 120);
        container.add(BranchDetails);
        BranchDetails.setFont(fontThree);
        BranchDetails.setCursor(cursor);

        BranchDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = JOptionPane.showInputDialog("Branch ID");
                branchID = Integer.parseInt(id);

                boolean flag = false;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Branch.* from Branch where Branch_ID = "+branchID+" ");

                    while (Display.next()) {
                        all_ID = Display.getInt(1);
                        branchName = Display.getString(2);
                        branchAddress = Display.getString(3);
                        if (all_ID == branchID)
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
                    JOptionPane.showMessageDialog(null, "Branch ID :  " + branchID +"\n" + "Name       :  " +branchName + "\n" + "Address   :  " +branchAddress);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Branch!", "Warring", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        //=====================================================================================
        //                            REMOVE BRANCH OPTION
        //=====================================================================================

        deleteBranch = new JButton("Remove Branch");
        deleteBranch.setBounds(100, 290, 200, 120);
        container.add(deleteBranch);
        deleteBranch.setFont(fontThree);
        deleteBranch.setCursor(cursor);

        deleteBranch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = JOptionPane.showInputDialog("Branch ID");
                branchID = Integer.parseInt(id);

                boolean flag = false;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet DisplayBalance = statement.executeQuery("select Branch_ID from Branch where Branch_ID = "+branchID+" ");

                    while (DisplayBalance.next()) {
                        all_ID = DisplayBalance.getInt(1);
                        if (all_ID == branchID)
                        {
                            flag = true;
                        }
                    }
                    if (flag)
                    {
                        PreparedStatement accountsDelete = connection.prepareStatement("DELETE FROM Branch WHERE Branch_ID = "+branchID+" ");
                        accountsDelete.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Delete Successful");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Branch!", "Warring", JOptionPane.WARNING_MESSAGE);
                    }
                    connection.close();
                }
                catch (Exception exception)
                {
                    System.out.println(exception);
                }

            }
        });


        // COPY RIGHT MESSAGE
        copyRight = new JLabel();
        copyRight.setText("Copy Right - Zero Pay!");
        copyRight.setFont(fontTwo);
        copyRight.setBounds(130, 420, 300, 20);
        container.add(copyRight);

    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setBounds(630, 300, 405, 490);
        setTitle("Branch");
    }

    public static void main(String[] args) {
        UserAccount user = new UserAccount();
        user.setVisible(true);
    }
}