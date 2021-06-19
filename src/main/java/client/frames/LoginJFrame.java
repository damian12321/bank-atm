package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.utils.FrameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginJFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Welcome in Damian's Bank, please login. ");
    private final JLabel accountIdLabel = new JLabel("Customer id: ");
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JTextField userTextField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("Login");
    private final JButton resetButton = new JButton("Reset");
    private final JButton createAccountButton = new JButton("Create new account");
    private final JButton exitAppButton = new JButton("Exit");
    private final JCheckBox showPassword = new JCheckBox("Show Password");
    private final JLabel informationMessage = new JLabel();


    public LoginJFrame() {
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
        welcomeTextLabel.setBounds(200, 10, 400, 100);
        accountIdLabel.setBounds(250, 150, 100, 30);
        passwordLabel.setBounds(250, 220, 100, 30);
        userTextField.setBounds(350, 150, 150, 30);
        passwordField.setBounds(350, 220, 150, 30);
        showPassword.setBounds(350, 250, 150, 30);
        loginButton.setBounds(250, 300, 100, 30);
        resetButton.setBounds(400, 300, 100, 30);
        createAccountButton.setBounds(450, 550, 200, 30);
        exitAppButton.setBounds(150, 550, 200, 30);
        informationMessage.setBounds(250, 320, 300, 100);
        informationMessage.setVisible(false);

    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        showPassword.setBackground(COLOR);
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(accountIdLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(createAccountButton);
        container.add(exitAppButton);
        container.add(informationMessage);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        createAccountButton.addActionListener(this);
        exitAppButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitAppButton) {
            this.dispose();
        }

        if (e.getSource() == loginButton) {
            boolean error = false;
            String text = "";

            if (userTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                error = true;
                text = "Data fields must not be empty.";
            }

            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setVisible(true);
            } else {
                if (userTextField.getText().equals("admin") && passwordField.getText().equals("admin")) {
                    new AdminFrame();
                    this.dispose();
                } else {
                    account = RESTClient.getAccount(Integer.parseInt(userTextField.getText()), passwordField.getText());
                    if (account == null) {
                        informationMessage.setVisible(true);
                        informationMessage.setText("The user name or password is incorrect.");
                        informationMessage.setForeground(Color.red);
                    } else {
                        new AccountPanelFrame(account);
                        this.dispose();
                    }
                }

            }

        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        if (e.getSource() == createAccountButton) {
            new CreateAccountFrame();
            this.dispose();
        }
    }

}