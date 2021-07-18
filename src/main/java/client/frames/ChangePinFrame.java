package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.utils.FrameSetup;
import client.utils.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePinFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private boolean pinChanged = false;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Change your pin number ");
    private final JLabel infoAboutPin = new JLabel("Pin number will be used to the account transactions. Only digits allowed. It must be at least four digits long.");
    private final JLabel oldPinNumber = new JLabel("Old pin number: ");
    private final JLabel accountsPin = new JLabel("Pin number: ");
    private final JLabel accountsPinConfirm = new JLabel("Pin number confirm: ");
    private final JLabel informationMessage = new JLabel();
    private final JLabel pinChangedMassage = new JLabel();
    private final JPasswordField oldPinNumberField = new JPasswordField();
    private final JPasswordField accountsPinField = new JPasswordField();
    private final JPasswordField accountsPinConfirmField = new JPasswordField();
    private final JCheckBox showPin = new JCheckBox("Show Pin");
    private final JButton resetButton = new JButton("Reset");
    private final JButton submitButton = new JButton("Submit");
    private final JButton accountBackButton = new JButton("Back to account");


    public ChangePinFrame(Account account) {
        this.account = account;
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
        oldPinNumber.setBounds(230, 100, 100, 30);
        accountsPin.setBounds(250, 170, 100, 30);
        accountsPinConfirm.setBounds(205, 240, 150, 30);
        oldPinNumberField.setBounds(350, 100, 150, 30);
        showPin.setBounds(350, 130, 150, 30);
        accountsPinField.setBounds(350, 170, 150, 30);
        accountsPinConfirmField.setBounds(350, 240, 150, 30);
        resetButton.setBounds(370, 280, 100, 30);
        submitButton.setBounds(450, 550, 200, 30);
        accountBackButton.setBounds(150, 550, 200, 30);
        infoAboutPin.setBounds(100, 205, 650, 30);
        informationMessage.setBounds(270, 550, 500, 100);
        pinChangedMassage.setBounds(220, 560, 700, 100);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        infoAboutPin.setForeground(Color.red);
        informationMessage.setVisible(false);
        pinChangedMassage.setVisible(false);
        pinChangedMassage.setFont(new Font("INK Free", Font.BOLD, 20));
        showPin.setBackground(COLOR);
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(oldPinNumber);
        container.add(oldPinNumberField);
        container.add(resetButton);
        container.add(submitButton);
        container.add(accountBackButton);
        container.add(accountsPin);
        container.add(accountsPinField);
        container.add(accountsPinConfirm);
        container.add(accountsPinConfirmField);
        container.add(infoAboutPin);
        container.add(informationMessage);
        container.add(pinChangedMassage);
        container.add(showPin);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        submitButton.addActionListener(this);
        accountBackButton.addActionListener(this);
        showPin.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            oldPinNumberField.setText("");
            accountsPinField.setText("");
            accountsPinConfirmField.setText("");
        }
        if (e.getSource() == accountBackButton) {
            String response = RESTClient.getResponseBody(account.getId(), Session.password);
            if (response.endsWith("not active.")) {
                new LoginJFrame();
            } else {
                new AccountPanelFrame(account);
            }
            this.dispose();
        }
        if (e.getSource() == showPin) {
            if (showPin.isSelected()) {
                oldPinNumberField.setEchoChar((char) 0);
            } else {
                oldPinNumberField.setEchoChar('*');
            }
        }
        if ((e.getSource() == submitButton) && !pinChanged) {
            boolean error = false;
            String text = "<html>";
            if (!accountsPinField.getText().equals(accountsPinConfirmField.getText())) {
                error = true;
                text = text + "Pin numbers are not the same.<br>";
            }
            if (accountsPinField.getText().equals(oldPinNumberField.getText() + "") && !error) {
                error = true;
                text = text + "Pin number cannot be the same as the old one.<br>";
            }
            char[] charsPin = accountsPinField.getText().toCharArray();
            for (char c : charsPin) {
                if (!Character.isDigit(c) || charsPin.length < 4) {
                    error = true;
                    text = text + "Pin number is not correct.<br>";
                    break;
                }
            }

            if (oldPinNumberField.getText().isEmpty() || accountsPinField.getText().isEmpty() || accountsPinConfirmField.getText().isEmpty()
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
                String response = RESTClient.changePin(account.getAccountNumber(), Integer.parseInt(oldPinNumberField.getText()),
                        Integer.parseInt(accountsPinField.getText()));
                if (response.equals("Pin number has been changed")) {
                    informationMessage.setVisible(false);
                    pinChangedMassage.setVisible(true);
                    pinChanged = true;
                    pinChangedMassage.setForeground(Color.green);
                    pinChangedMassage.setText("<html>Pin number has been changed.</html>");
                } else {
                    informationMessage.setVisible(true);
                    informationMessage.setText(response);
                    informationMessage.setForeground(Color.red);
                }

            }
        }
    }

}