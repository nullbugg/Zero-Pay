package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Employee extends JFrame {

    private JLabel copyRight;
    private JButton deleteEmployee,logOut,Employee,SearchEmployee;
    private Cursor cursor;

    String username = AdminLogin.username;
    double employeeSalary = 0;
    String employeeName = "", employeePhone ="",branchName = "";
    String name = "";
    int employeeID,branchID,all_ID;
    double salary;




    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public Employee() {

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
        //                              ADD EMPLOYEE OPTION
        //=====================================================================================

        Employee = new JButton("Add Employee");
        Employee.setBounds(100, 50, 200, 120);
        container.add(Employee);
        Employee.setFont(fontThree);
        Employee.setCursor(cursor);
        Employee.setForeground(new Color(1, 1, 27));

        Employee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ID = JOptionPane.showInputDialog("Branch ID       Branch Name\n" + "   101                Branch_A\n"
                        + "   201                Branch_B\n" + "   301                Branch_C\n");
                branchID = Integer.parseInt(ID);
                boolean flag = false;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, "root", "");


                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Branch_ID from Branch where Branch_ID = " + branchID + " ");

                    while (Display.next()) {
                        all_ID = Display.getInt(1);
                        if (all_ID == branchID) {
                            flag = true;
                        }
                    }

                    connection.close();
                } catch (Exception exception) {
                    System.out.println(exception);
                }

                if (flag) {

                    employeeName = JOptionPane.showInputDialog("Employee Name");

                    if (!(employeeName.isEmpty())) {
                        employeePhone = JOptionPane.showInputDialog("Employee Phone");

                        if (!(employeePhone.isEmpty())) {
                            String salary = JOptionPane.showInputDialog("Employee Salary");
                            employeeSalary = Double.parseDouble(salary);


                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url, "root", "");

                                PreparedStatement newBranch = connection.prepareStatement("INSERT INTO Employee (Employee_Name,Phone,Salary,Branch_ID) VALUES ('" + employeeName + "','" + employeePhone + "'," + employeeSalary + " ," + branchID + ")");
                                newBranch.executeUpdate();

                                Statement statement = connection.createStatement();
                                ResultSet Display = statement.executeQuery("select Employee_ID from Employee where Employee_Name = '" + employeeName + "' ");

                                while (Display.next()) {
                                    employeeID = Display.getInt(1);
                                }

                                JOptionPane.showMessageDialog(null, "       Employee ID is " + employeeID + "\n" + "Employee Added Successfully!");
                                connection.close();
                            } catch (Exception exception) {
                                System.out.println(exception);
                            }

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Invalid Input!", "Warring", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Input!", "Warring", JOptionPane.WARNING_MESSAGE);
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Branch ID!", "Warring", JOptionPane.WARNING_MESSAGE);
                }

            }



        });

        //=====================================================================================
        //                              SEARCH EMPLOYEE OPTION
        //=====================================================================================

        SearchEmployee = new JButton("Search Employee");
        SearchEmployee.setBounds(100, 170, 200, 120);
        container.add(SearchEmployee);
        SearchEmployee.setFont(fontThree);
        SearchEmployee.setCursor(cursor);

        SearchEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = JOptionPane.showInputDialog("Employee ID");
                employeeID = Integer.parseInt(id);

                boolean flag = false;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet Display = statement.executeQuery("select Employee_ID,Employee_Name,Phone,Salary,Branch_Name from Employee join Branch on Employee.Branch_ID = Branch.Branch_ID where Employee_ID = "+employeeID+" ");


                    while (Display.next()) {
                        all_ID = Display.getInt(1);
                        employeeName = Display.getString(2);
                        employeePhone = Display.getString(3);
                        salary = Display.getDouble(4);
                        branchName = Display.getString(5);
                        if (all_ID == employeeID)
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
                    JOptionPane.showMessageDialog(null, "ID         :  " + employeeID +"\n" + "Name   :  " + employeeName + "\n" + "Phone   :  " + employeePhone +"\n" + "Salary   :  " + salary +"\n" + "Branch  : " + branchName);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid Employee ID!", "Warring", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        //=====================================================================================
        //                             REMOVE EMPLOYEE OPTION
        //=====================================================================================

        deleteEmployee = new JButton("Remove Employee");
        deleteEmployee.setBounds(100, 290, 200, 120);
        container.add(deleteEmployee);
        deleteEmployee.setFont(fontThree);
        deleteEmployee.setCursor(cursor);

        deleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = JOptionPane.showInputDialog("Employee ID");
                employeeID = Integer.parseInt(id);

                boolean flag = false;
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet DisplayBalance = statement.executeQuery("select Employee_ID from Employee where Employee_ID = "+employeeID+" ");

                    while (DisplayBalance.next()) {
                        all_ID = DisplayBalance.getInt(1);
                        if (all_ID == employeeID)
                        {
                            flag = true;
                        }
                    }
                    if (flag)
                    {
                        PreparedStatement accountsDelete = connection.prepareStatement("DELETE FROM Employee WHERE Employee_ID = "+employeeID+" ");
                        accountsDelete.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Delete Successful");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Employee ID!", "Warring", JOptionPane.WARNING_MESSAGE);
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
        setTitle("Employee");
    }

    public static void main(String[] args) {
        UserAccount user = new UserAccount();
        user.setVisible(true);
    }
}