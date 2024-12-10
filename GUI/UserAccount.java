package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;

public class UserAccount extends JFrame {

    private JLabel accountHolderName,copyRight;
    private JButton transaction,deposit,withdraw,sendMoney,deleteAccount,logOut,myBalance;
    private Cursor cursor;

    String phone = Login.userPhone;
    String acNumber = "";
    String inputPass = "";
    String inputDeposit = "",withdrawAmount = "";
    String sendAmount;
    String name = "";
    String uName = "", uEmail = "",uPhone = "",uAmount = "",uPassword = "",uTransaction = "";
    double mainBalance = 0;


    // Font
    Font fontOne = new Font("Arial", Font.BOLD, 40);
    Font fontTwo = new Font("Thoma", Font.BOLD, 12);
    Font fontThree = new Font("cambria", Font.BOLD, 18);

    public UserAccount() {

        mainFrame();

        // Variables
        Container container = this.getContentPane();
        container.setBackground(Color.LIGHT_GRAY);
        container.setLayout(null);

        cursor = new Cursor(Cursor.HAND_CURSOR);

        File file = new File("src/Data/" + phone + ".txt");
        try {
            Scanner sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) {
                count++;
                String line = sc.nextLine();
                if (count == 1){
                    name = line;
                }
                if (count == 5){
                    mainBalance = Double.parseDouble(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                Login login = new Login();
                login.setVisible(true);
            }
        });


        // BALANCE OPTION
        myBalance = new JButton("Check Balance");
        myBalance.setBounds(3, 50, 200, 120);
        container.add(myBalance);
        myBalance.setFont(fontThree);
        myBalance.setCursor(cursor);
        myBalance.setForeground(new Color(1, 1, 27));
        //myBalance.setBackground(new Color(155, 153, 237));

        myBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Scanner sc = new Scanner(file);
                    int count = 0;
                    while (sc.hasNextLine()) {
                        count++;
                        String line = sc.nextLine();
                        if (count == 5){
                            mainBalance = Double.parseDouble(line);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
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
                    JOptionPane.showMessageDialog(null, "You can't send-money on your own account!", "Warring", 0);
                }
                else{
                    File file = new File("src/Data/" + acNumber + ".txt");
                    try {
                        Scanner sc = new Scanner(file);
                        finalFlag = true;
                    } catch (FileNotFoundException ex) {
                        finalFlag = false;
                        JOptionPane.showMessageDialog(null, " Invalid Acccount Number!", "Warring", 2);
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
                                JOptionPane.showMessageDialog(null, "Invalid Amount!","Warring",2);
                            }
                        }
                        int count = 0;
                        while (count < 3){
                            count++;
                            inputPass = JOptionPane.showInputDialog("Confirm Your Password");
                            if (checkPass()){
                                double x = Double.parseDouble(sendAmount);
                                mainBalance -= x;
                                String updateBalance ="";
                                updateBalance = Double.toString(mainBalance);
                                updateFile(phone);
                                try {
                                    FileWriter fileWriter = new FileWriter(file);
                                    fileWriter.write(uName);
                                    fileWriter.write("\n");
                                    fileWriter.write(uEmail);
                                    fileWriter.write("\n");
                                    fileWriter.write(uPhone);
                                    fileWriter.write("\n");
                                    fileWriter.write(uPassword);
                                    fileWriter.write("\n");
                                    fileWriter.write(updateBalance);
                                    fileWriter.write("\n");
                                    fileWriter.write("Send Money: $" + sendAmount);
                                    fileWriter.write("\n");
                                    fileWriter.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                updateFile(acNumber);
                                File file2 = new File("src/Data/" + acNumber + ".txt");
                                try {
                                    FileWriter fileWriter = new FileWriter(file2);
                                    fileWriter.write(uName);
                                    fileWriter.write("\n");
                                    fileWriter.write(uEmail);
                                    fileWriter.write("\n");
                                    fileWriter.write(uPhone);
                                    fileWriter.write("\n");
                                    fileWriter.write(uPassword);
                                    fileWriter.write("\n");
                                    double temp = Double.parseDouble(uAmount) + x;
                                    String s = Double.toString(temp);
                                    fileWriter.write(s);
                                    fileWriter.write("\n");
                                    fileWriter.write("Received Money: $" + sendAmount);
                                    fileWriter.write("\n");
                                    fileWriter.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
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
                    mainBalance += temp;
                    updateFile(phone);
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(uName);
                        fileWriter.write("\n");
                        fileWriter.write(uEmail);
                        fileWriter.write("\n");
                        fileWriter.write(uPhone);
                        fileWriter.write("\n");
                        fileWriter.write(uPassword);
                        fileWriter.write("\n");
                        String s = Double.toString(mainBalance);
                        fileWriter.write(s);
                        fileWriter.write("\n");
                        fileWriter.write("Deposit: $" + inputDeposit);
                        fileWriter.write("\n");
                        fileWriter.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Deposit Successful");
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
                            mainBalance -= x;
                            updateFile(phone);
                            try {
                                FileWriter fileWriter = new FileWriter(file);
                                fileWriter.write(uName);
                                fileWriter.write("\n");
                                fileWriter.write(uEmail);
                                fileWriter.write("\n");
                                fileWriter.write(uPhone);
                                fileWriter.write("\n");
                                fileWriter.write(uPassword);
                                fileWriter.write("\n");
                                String s = Double.toString(mainBalance);
                                fileWriter.write(s);
                                fileWriter.write("\n");
                                fileWriter.write("Withdraw: $" + withdrawAmount);
                                fileWriter.write("\n");
                                fileWriter.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
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


        // ALL TRANSACTION OPTION

        transaction = new JButton("Last Transaction");
        transaction.setBounds(3, 290, 200, 120);
        container.add(transaction);
        transaction.setFont(fontThree);
        transaction.setCursor(cursor);

        transaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String showTransaction = "";
                try {
                    Scanner sc = new Scanner(file);
                    int count = 0;
                    while (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        count++;
                        if (count == 6){
                            showTransaction = str;
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
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
                    try {
                        if (file.delete()){
                            JOptionPane.showMessageDialog(null, "Account Delete Successfully");
                            dispose();
                            Login login = new Login();
                            login.setVisible(true);
                        }
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Account Delete Unsuccessfully","Warring",-1);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Password!","Warring",0);
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
        File file = new File("src/Data/" + phone + ".txt");
        try {
            Scanner sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) {
                count++;
                String line = sc.nextLine();
                if (count == 5){
                    mainBalance = Double.parseDouble(line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        double x = Double.parseDouble(newAmount);
        if (mainBalance >= x){
            return true;
        }

        return false;
    }

    public void updateFile(String fileName){
        File file = new File("src/Data/" + fileName + ".txt");
        try {
            Scanner sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) {
                count++;
                String str = sc.nextLine();
                if (count == 1){
                    uName = str;
                }
                else if (count == 2){
                    uEmail = str;
                }
                else if (count == 3){
                    uPhone = str;
                }
                else if (count == 4){
                    uPassword = str;
                }
                else if (count == 5){
                    uAmount = str;
                }
                else {
                    uTransaction = str;
                }
            }
        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
        }
    }

    public boolean checkPass(){
        File file = new File("src/Data/" + phone + ".txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str.equals(inputPass)){
                     return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
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