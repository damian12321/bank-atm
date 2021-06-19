package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.utils.FrameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private boolean pinChanged = false;
    private final String oldPwd;
    private final boolean isAdmin;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Change your password ");
    private final JLabel oldPassword = new JLabel("Old password: ");
    private final JLabel customersPassword = new JLabel("Password: ");
    private final JLabel customersPasswordConfirm = new JLabel("Password confirm: ");
    private final JLabel informationMessage = new JLabel();
    private final JLabel passwordChangedMassage = new JLabel();
    private final JPasswordField oldPasswordField = new JPasswordField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField passwordConfirmField = new JPasswordField();
    private final JCheckBox showPassword = new JCheckBox("Show Password");
    private final JButton resetButton = new JButton("Reset");
    private final JButton submitButton = new JButton("Submit");
    private final JButton accountBackButton = new JButton("Back to account");


    public ChangePasswordFrame(Account account) {
        this.account = account;
        this.oldPwd = account.getPassword();
        this.isAdmin = false;
        setFrameManager();
        setLayoutManager();
        setLocationAndSize();
        setProperties();
        addComponentsToContainer();
        addActionEvent();
    }

    public ChangePasswordFrame(Account account, boolean isAdmin) {
        this.account = account;
        this.oldPwd = account.getPassword();
        this.isAdmin = isAdmin;
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
            if (!isAdmin) {
                new AccountPanelFrame(account);
            } else {
                new AdminFrame();
            }
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
                account.setPassword(passwordConfirmField.getText());
                Account response = RESTClient.updateAccount(account);
                if (response.getId() != 0) {
                    account = response;
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