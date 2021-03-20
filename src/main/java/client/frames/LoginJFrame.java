package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginJFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Customer customer;
    Container container = getContentPane();
    JLabel welcomeTextLabel = new JLabel("Welcome in Damian's Bank, please login. ");
    JLabel accountIdLabel = new JLabel("Account id: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton createAccountButton = new JButton("Create new account");
    JButton exitAppButton = new JButton("Exit");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel informationMessage = new JLabel();


    public LoginJFrame() {
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
        int iCoordX = (objDimension.width - this.getWidth()) / 2;
        int iCoordY = (objDimension.height - this.getHeight()) / 2;
        this.setLocation(iCoordX, iCoordY);
        this.getContentPane().setBackground(new Color(227, 227, 227));
        this.setResizable(false);
        this.setVisible(true);
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

            if (userTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
                error = true;
                text = "Data fields must not be empty.";
            }

            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setVisible(true);
            } else {
                customer = RESTClient.getCustomer(Integer.parseInt(userTextField.getText()),passwordField.getText());
                if(customer==null){
                    informationMessage.setVisible(true);
                    informationMessage.setText("The user name or password is incorrect.");
                    informationMessage.setForeground(Color.red);
                }else
                {
                    System.out.println(customer);
                   new AccountPanelFrame(customer);
                   this.dispose();
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