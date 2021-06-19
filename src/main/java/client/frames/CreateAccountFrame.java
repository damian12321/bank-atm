package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.utils.FrameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private boolean customerAdded = false;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Create new account");
    private final JLabel infoAboutPin = new JLabel("Pin number will be used to the account transactions. Only digits allowed. It must be at least four digits long.");
    private final JLabel customersName = new JLabel("First name: ");
    private final JLabel customersLastName = new JLabel("Last name: ");
    private final JLabel customersPassword = new JLabel("Password: ");
    private final JLabel customersPasswordConfirm = new JLabel("Password confirm: ");
    private final JLabel accountsPin = new JLabel("Pin number: ");
    private final JLabel accountsPinConfirm = new JLabel("Pin number confirm: ");
    private final JLabel informationMessage = new JLabel();
    private final JLabel accountCreatedMessage = new JLabel();
    private final JTextField customersNameField = new JTextField();
    private final JTextField customersLastNameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField passwordConfirmField = new JPasswordField();
    private final JPasswordField accountsPinField = new JPasswordField();
    private final JPasswordField accountsPinConfirmField = new JPasswordField();
    private final JButton resetButton = new JButton("Reset");
    private final JButton createAccountButton = new JButton("Create account");
    private final JButton loginBackButton = new JButton("Back to login page");


    public CreateAccountFrame() {
        setFrameManager();
        setLayoutManager();
        setLocationAndSize();
        setProperties();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setFrameManager() {
        FrameSetup.setupFrame(this);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        welcomeTextLabel.setBounds(300, 20, 400, 30);
        customersName.setBounds(250, 100, 100, 30);
        customersLastName.setBounds(250, 170, 100, 30);
        customersPassword.setBounds(250, 240, 100, 30);
        customersPasswordConfirm.setBounds(205, 310, 150, 30);
        customersNameField.setBounds(350, 100, 150, 30);
        customersLastNameField.setBounds(350, 170, 150, 30);
        passwordField.setBounds(350, 240, 150, 30);
        passwordConfirmField.setBounds(350, 310, 150, 30);
        resetButton.setBounds(370, 490, 100, 30);
        createAccountButton.setBounds(450, 550, 200, 30);
        loginBackButton.setBounds(150, 550, 200, 30);
        accountsPin.setBounds(245, 380, 200, 30);
        accountsPinField.setBounds(350, 380, 150, 30);
        accountsPinConfirm.setBounds(200, 450, 200, 30);
        accountsPinConfirmField.setBounds(350, 450, 150, 30);
        infoAboutPin.setBounds(100, 415, 650, 30);
        informationMessage.setBounds(250, 550, 500, 100);
        accountCreatedMessage.setBounds(100, 560, 700, 100);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        infoAboutPin.setForeground(Color.red);
        informationMessage.setVisible(false);
        accountCreatedMessage.setVisible(false);
        accountCreatedMessage.setFont(new Font("INK Free", Font.BOLD, 20));
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(customersName);
        container.add(customersNameField);
        container.add(customersLastName);
        container.add(customersLastNameField);
        container.add(customersPassword);
        container.add(passwordField);
        container.add(customersPasswordConfirm);
        container.add(passwordConfirmField);
        container.add(resetButton);
        container.add(createAccountButton);
        container.add(loginBackButton);
        container.add(accountsPin);
        container.add(accountsPinField);
        container.add(accountsPinConfirm);
        container.add(accountsPinConfirmField);
        container.add(infoAboutPin);
        container.add(informationMessage);
        container.add(accountCreatedMessage);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        createAccountButton.addActionListener(this);
        loginBackButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            customersNameField.setText("");
            customersLastNameField.setText("");
            passwordField.setText("");
            passwordConfirmField.setText("");
            accountsPinField.setText("");
            accountsPinConfirmField.setText("");
        }
        if (e.getSource() == loginBackButton) {
            new LoginJFrame();
            this.dispose();
        }

        if ((e.getSource() == createAccountButton) && !customerAdded) {
            boolean error = false;
            String text = "<html>";
            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                error = true;
                text = text + "Passwords are not the same.<br>";
            }
            if (!accountsPinField.getText().equals(accountsPinConfirmField.getText())) {
                error = true;
                text = text + "Pin numbers are not the same.<br>";
            }
            char[] charsPin = accountsPinField.getText().toCharArray();
            for (char c : charsPin) {
                if (!Character.isDigit(c) || charsPin.length < 4) {
                    error = true;
                    text = text + "Pin number is not correct.<br>";
                }
            }

            if (customersNameField.getText().isEmpty() || customersLastNameField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || accountsPinField.getText().isEmpty()) {
                error = true;
                text = text + "Data fields must not be empty.<br>";
            }

            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setText(text + "</html>");
                informationMessage.setVisible(true);
            } else {
                int accountNumber = RESTClient.getFreeAccountNumber();
                account = new Account(customersNameField.getText(), customersLastNameField.getText(), passwordField.getText(),
                        accountNumber, Integer.parseInt(accountsPinField.getText()));
                Account response = RESTClient.createAccount(account);
                if (response.getId() != 0) {
                    accountCreatedMessage.setVisible(true);
                    informationMessage.setVisible(false);
                    customerAdded = true;
                    accountCreatedMessage.setText("<html>Your account has been added. Your account id number is: " + response.getId() +
                            ".<br>You can log in now.</html>");
                    accountCreatedMessage.setForeground(Color.green);
                    JOptionPane.showMessageDialog(this, "Your account has been added. Your customer id is: " + response.getId() +
                            ".You can log in now.");
                } else {
                    informationMessage.setVisible(true);
                    informationMessage.setText("Something went wrong.");
                    informationMessage.setForeground(Color.red);
                }

            }
        }
    }

}