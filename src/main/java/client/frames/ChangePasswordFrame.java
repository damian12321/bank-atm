package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Customer customer;
    private Account account;
    private boolean pinChanged = false;
    private String oldPwd;
    Container container = getContentPane();
    JLabel welcomeTextLabel = new JLabel("Change your password ");
    JLabel oldPassword = new JLabel("Old password: ");
    JLabel customersPassword = new JLabel("Password: ");
    JLabel customersPasswordConfirm = new JLabel("Password confirm: ");
    JLabel informationMessage = new JLabel();
    JLabel passwordChangedMassage = new JLabel();
    JPasswordField oldPasswordField = new JPasswordField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordConfirmField = new JPasswordField();
    JCheckBox showPassword = new JCheckBox("Show Password");
    JButton resetButton = new JButton("Reset");
    JButton submitButton = new JButton("Submit");
    JButton accountBackButton = new JButton("Back to account");


    public ChangePasswordFrame(Customer customer) {
        this.customer = customer;
        this.account = customer.getAccount();
        this.oldPwd = customer.getPassword();
        setFrameManager();
        setLayoutManager();
        setLocationAndSize();
        setProperties();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setFrameManager() {
        this.setTitle("Damian's Bank");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 700));
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCordX = (objDimension.width - this.getWidth()) / 2;
        int iCordY = (objDimension.height - this.getHeight()) / 2;
        this.setLocation(iCordX, iCordY);
        this.getContentPane().setBackground(COLOR);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        welcomeTextLabel.setBounds(300, 20, 400, 30);
        oldPassword.setBounds(230, 100, 100, 30);
        oldPasswordField.setBounds(350, 100, 150, 30);
        showPassword.setBounds(350, 130, 150, 30);
        customersPassword.setBounds(250, 170, 100, 30);
        passwordField.setBounds(350, 170, 150, 30);
        customersPasswordConfirm.setBounds(205, 240, 150, 30);
        passwordConfirmField.setBounds(350, 240, 150, 30);
        resetButton.setBounds(370, 310, 100, 30);
        submitButton.setBounds(450, 550, 200, 30);
        accountBackButton.setBounds(150, 550, 200, 30);
        informationMessage.setBounds(270, 550, 500, 100);
        passwordChangedMassage.setBounds(220, 560, 700, 100);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        ;
        informationMessage.setVisible(false);
        passwordChangedMassage.setVisible(false);
        passwordChangedMassage.setFont(new Font("INK Free", Font.BOLD, 20));
        showPassword.setBackground(COLOR);
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(oldPassword);
        container.add(oldPasswordField);
        container.add(resetButton);
        container.add(submitButton);
        container.add(accountBackButton);
        container.add(passwordField);
        container.add(passwordConfirmField);
        container.add(customersPassword);
        container.add(customersPasswordConfirm);
        container.add(informationMessage);
        container.add(passwordChangedMassage);
        container.add(showPassword);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        submitButton.addActionListener(this);
        accountBackButton.addActionListener(this);
        showPassword.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            oldPasswordField.setText("");
            passwordField.setText("");
            passwordConfirmField.setText("");
        }
        if (e.getSource() == accountBackButton) {
            new AccountPanelFrame(customer);
            this.dispose();
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                oldPasswordField.setEchoChar((char) 0);
            } else {
                oldPasswordField.setEchoChar('*');
            }
        }
        if ((e.getSource() == submitButton) && !pinChanged) {
            boolean error = false;
            String text = "<html>";
            if (!oldPasswordField.getText().equals(oldPwd + "")) {
                error = true;
                text = text + "Old password is not correct.<br>";
            }
            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                error = true;
                text = text + "Passwords are not the same.<br>";
            }
            if (passwordField.getText().equals(oldPwd) && !error) {
                error = true;
                text = text + "New password cannot be the same as the old one.<br>";
            }

            if (oldPasswordField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordConfirmField.getText().isEmpty()) {
                error = true;
                text = text + "Data fields must not be empty.<br>";
            }

            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setText(text + "</html>");
                informationMessage.setVisible(true);
            } else {
                customer.setPassword(passwordConfirmField.getText());
                Customer response = RESTClient.updateCustomer(customer);
                if (response.getId() != 0) {
                    customer = response;
                    informationMessage.setVisible(false);
                    passwordChangedMassage.setVisible(true);
                    pinChanged = true;
                    passwordChangedMassage.setText("<html>Your password has been changed.</html>");
                    passwordChangedMassage.setForeground(Color.green);
                } else {
                    informationMessage.setVisible(true);
                    informationMessage.setText("Something went wrong.");
                    informationMessage.setForeground(Color.red);
                }

            }
        }
    }

}