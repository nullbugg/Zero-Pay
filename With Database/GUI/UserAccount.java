package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class UserAccount extends JFrame {

    private JLabel accountHolderName,copyRight;
    private JButton deposit,withdraw,sendMoney,deleteAccount,logOut,myBalance;
    private Cursor cursor;

    String phone = UserLogin.userPhone;
    String acNumber = "",receiver;
    String inputPass = "";
    String inputDeposit = "",withdrawAmount = "";
    String sendAmount;
    String name = "";
    double mainBalance = 0;
    String storedPassword,showTransaction;



    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public UserAccount() {

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
            ResultSet DisplayName = statement.executeQuery("select Full_Name from Customer_Info where Phone = "+phone+" ");

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
                UserLogin userLogin = new UserLogin();
                userLogin.setVisible(true);
            }
        });


        // CHECK BALANCE OPTION
        myBalance = new JButton("Check Balance");
        myBalance.setBounds(3, 50, 200, 120);
        container.add(myBalance);
        myBalance.setFont(fontThree);
        myBalance.setCursor(cursor);
        myBalance.setForeground(new Color(1, 1, 27));

        myBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Database Read
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");

                    Statement statement = connection.createStatement();
                    ResultSet DisplayBalance = statement.executeQuery("select Current_Balance from Accounts_Balance where Phone = "+phone+" ");

                    while (DisplayBalance.next()) {
                        mainBalance = DisplayBalance.getDouble(1);
                    }
                    connection.close();
                }
                catch (Exception exception)
                {
                    System.out.println(exception);
                }

                JOptionPane.showMessageDialog(null, "\nCurrent Balance: $" + mainBalance,"Balance",JOptionPane.PLAIN_MESSAGE);
            }
        });

        // SEND MONEY OPTION
        sendMoney = new JButton("Send Money");
        sendMoney.setBounds(203, 50, 200, 120);
        container.add(sendMoney);
        sendMoney.setFont(fontThree);
        sendMoney.setCursor(cursor);

        sendMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean finalFlag = false;

                acNumber = JOptionPane.showInputDialog("Account Number");

                if (acNumber.equals(phone)){
                    JOptionPane.showMessageDialog(null, "You can't send-money on your own account!", "Warring", JOptionPane.ERROR_MESSAGE);
                }
                else{

                    // Database Read
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        Statement statement = connection.createStatement();
                        ResultSet DisplayPhone = statement.executeQuery("select Phone from Customer_Info where Phone = "+acNumber+" ");

                        while (DisplayPhone.next()) {
                            receiver = DisplayPhone.getString(1);
                        }
                        connection.close();

                        if (!(receiver.equals(acNumber)))
                        {
                            JOptionPane.showMessageDialog(null, " Invalid Account Number!", "Warring", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                        {
                            finalFlag = true;
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }

                    if (finalFlag){
                        boolean amountFlag = false;
                        while (true){
                            sendAmount = JOptionPane.showInputDialog("Amount");
                            double x = Double.parseDouble(sendAmount);
                            if (checkAmount(sendAmount) && x > 0){
                                amountFlag = true;
                                break;
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Invalid Amount!","Warring", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        int count = 0;
                        while (count < 3){
                            count++;
                            inputPass = JOptionPane.showInputDialog("Enter Your Password");
                            if (checkPass() && amountFlag){
                                double x = Double.parseDouble(sendAmount);

                                // Database Update

                                try
                                {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection connection = DriverManager.getConnection(url,"root","");

                                    PreparedStatement senderUpdate = connection.prepareStatement("UPDATE Accounts_Balance SET Current_Balance = Current_Balance - "+x+", Transaction = '"+sendAmount+" TK Send to "+acNumber+"' WHERE Phone = "+phone+" ");
                                    senderUpdate.executeUpdate();

                                    PreparedStatement receiverUpdate = connection.prepareStatement("UPDATE Accounts_Balance SET Current_Balance = Current_Balance + "+x+", Transaction = '"+sendAmount+" TK Received From "+phone+"' WHERE Phone = "+acNumber+" ");
                                    receiverUpdate.executeUpdate();
                                    connection.close();

                                }
                                catch (Exception ex)
                                {
                                    System.out.println(ex);
                                }

                                JOptionPane.showMessageDialog(null, "Successfully!");
                                break;
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Incorrect Password!");
                            }
                        }
                    }
                }

            }
        });


        // DEPOSIT OPTION
        deposit = new JButton("Deposit");
        deposit.setBounds(3, 170, 200, 120);
        container.add(deposit);
        deposit.setFont(fontThree);
        deposit.setCursor(cursor);

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputDeposit = JOptionPane.showInputDialog(null,"Deposit Amount","Input",JOptionPane.PLAIN_MESSAGE);
                double temp = Double.parseDouble(inputDeposit);
                if (temp > 0){

                    // Database Update

                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        PreparedStatement senderUpdate = connection.prepareStatement("UPDATE Accounts_Balance SET Current_Balance = Current_Balance + "+temp+", Transaction = '"+temp+" TK Deposited' WHERE Phone = "+phone+" ");
                        senderUpdate.executeUpdate();
                        connection.close();

                    }
                    catch (Exception exception)
                    {
                        System.out.println(e);
                    }
                    JOptionPane.showMessageDialog(null, "Deposited Successful");
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid Amount!","Warring",2);
                }

            }
        });


        // WITHDRAW OPTION
        withdraw = new JButton("Withdraw");
        withdraw.setBounds(203, 170, 200, 120);
        container.add(withdraw);
        withdraw.setFont(fontThree);
        withdraw.setCursor(cursor);

        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean flag = false;
                while (true){
                    withdrawAmount = JOptionPane.showInputDialog(null,"Amount","Input",-1);
                    double temp = Double.parseDouble(withdrawAmount);
                    if (checkAmount(withdrawAmount) && temp > 0){
                        if (temp < 10){
                            JOptionPane.showMessageDialog(null, "Minimum Withdrawal Amount is $10","Warring",0);
                        }else{
                            flag = true;
                            break;
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid Amount!","Warring",0);
                    }
                }
                if (flag){
                    while (true){
                        inputPass = JOptionPane.showInputDialog(null,"Confirm Your Password","Input",-1);
                        if (checkPass()){
                            double x = Double.parseDouble(withdrawAmount);

                            // Database Update

                            try
                            {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url,"root","");

                                PreparedStatement senderUpdate = connection.prepareStatement("UPDATE Accounts_Balance SET Current_Balance = Current_Balance - "+x+", Transaction = '"+x+" TK Withdraw' WHERE Phone = "+phone+" ");
                                senderUpdate.executeUpdate();
                                connection.close();

                            }
                            catch (Exception ex)
                            {
                                System.out.println(ex);
                            }
                            JOptionPane.showMessageDialog(null, "Withdraw Successful!","Message",1);
                            break;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Incorrect Password!","Warring",0);
                        }
                    }
                }
            }
        });


        // PREVIOUS TRANSACTION OPTION

        JButton transaction = new JButton("Last Transaction");
        transaction.setBounds(3, 290, 200, 120);
        container.add(transaction);
        transaction.setFont(fontThree);
        transaction.setCursor(cursor);

        transaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Database Read

                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url,"root","");
                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("select Transaction from Accounts_Balance where Phone = "+phone+" ");

                    while (resultSet.next()) {
                        showTransaction = resultSet.getString(1);
                    }
                    connection.close();

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
                JOptionPane.showMessageDialog(null, showTransaction);
            }
        });


        // DELETE ACCOUNT SECTION
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(203, 290, 200, 120);
        container.add(deleteAccount);
        deleteAccount.setFont(fontThree);
        deleteAccount.setCursor(cursor);


        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                inputPass = JOptionPane.showInputDialog("Confirm Your Password!");
                boolean flag = false;
                if (inputPass != null && checkPass()){
                    flag = true;
                }

                if (flag){
                    // Database Read

                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url,"root","");

                        PreparedStatement accountsDelete = connection.prepareStatement("DELETE FROM Customer_Info WHERE Phone = "+phone+" ");
                        accountsDelete.executeUpdate();

                        connection.close();
                        JOptionPane.showMessageDialog(null, "Account Delete Successfully");
                        dispose();
                        UserLogin userLogin = new UserLogin();
                        userLogin.setVisible(true);

                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Account Delete Unsuccessfully","Warring", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Password!","Warring", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        // COPY RIGHT MESSAGE
        copyRight = new JLabel();
        copyRight.setText("Copy Right - Zero Pay!");
        copyRight.setFont(fontTwo);
        copyRight.setBounds(130, 420, 300, 20);
        container.add(copyRight);

        JOptionPane.showMessageDialog(null,"Login Successful");
    }

    public boolean checkAmount(String newAmount){

        // Database Read

        try
        {
            String url = "jdbc:mysql://localhost:3306/Zero_Pay";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","");

            Statement statement = connection.createStatement();
            ResultSet DisplayBalance = statement.executeQuery("select Current_Balance from Accounts_Balance where Phone = "+phone+" ");

            while (DisplayBalance.next()) {
                mainBalance = DisplayBalance.getDouble(1);
            }
            connection.close();
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }

        double x = Double.parseDouble(newAmount);
        if (mainBalance >= x){
            return true;
        }

        return false;
    }

    public boolean checkPass(){

        // Database
        String url = "jdbc:mysql://localhost:3306/Zero_Pay";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select Password from Customer_Info where Phone = "+phone+" ");

            while (resultSet.next()) {
                storedPassword = resultSet.getString(1);
            }
            connection.close();

            if (storedPassword.equals(inputPass)){
                return true;
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }

        return false;
    }

    public void mainFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setBounds(630, 300, 405, 490);
        setTitle("Profile!");
    }

    public static void main(String[] args) {
        UserAccount user = new UserAccount();
        user.setVisible(true);
    }
}