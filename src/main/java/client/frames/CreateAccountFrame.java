package client.frames;

import client.entity.Account;
import client.entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Customer customer;
    private Account account;
    Container container = getContentPane();
    JLabel welcomeTextLabel = new JLabel("Create new account");
    JLabel infoAboutPin = new JLabel("Pin number will be used to the account transactions. Only digits allowed.");
    JLabel customersName = new JLabel("First name: ");
    JLabel customersLastName = new JLabel("Last name: ");
    JLabel customersPassword = new JLabel("Password: ");
    JLabel customersPasswordConfirm = new JLabel("Password confirm: ");
    JLabel accountsPin = new JLabel("Pin number: ");
    JLabel accountsPinConfirm = new JLabel("Pin number confirm: ");
    JLabel informationMassage = new JLabel();
    JTextField customersNameField = new JTextField();
    JTextField customersLastNameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordConfirmField = new JPasswordField();
    JPasswordField accountsPinField = new JPasswordField();
    JPasswordField accountsPinConfirmField = new JPasswordField();
    JButton resetButton = new JButton("Reset");
    JButton createAccountButton = new JButton("Create account");
    JButton loginBackButton = new JButton("Back to login page");


    public CreateAccountFrame() {
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
        this.getContentPane().setBackground(new Color(227, 227, 227));
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        welcomeTextLabel.setBounds(300, 10, 400, 100);
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
        infoAboutPin.setBounds(250, 410, 450, 30);
        informationMassage.setBounds(250, 550, 300, 100);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        infoAboutPin.setForeground(Color.red);
        informationMassage.setVisible(false);
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
        container.add(informationMassage);
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
        if (e.getSource() == createAccountButton) {
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
            char[] chars = accountsPinField.getText().toCharArray();
            for(char c : chars){
                if(!Character.isDigit(c)){
                    error = true;
                    text = text + "Pin number is not correct.<br>";
                }
            }
            if(customersNameField.getText().isEmpty()||customersLastNameField.getText().isEmpty()||passwordField.getText().isEmpty()
            ||accountsPinField.getText().isEmpty())
            {
                error = true;
                text = text + "Data fields must not be empty.<br>";
            }

            if (error) {
                informationMassage.setForeground(Color.red);
                informationMassage.setText(text);
                informationMassage.setText(text+"</html>");
                informationMassage.setVisible(true);
            } else {
//                Account account=new Account()
                informationMassage.setText("Your account has been added. You can log in now.");
                informationMassage.setForeground(Color.green);

            }
        }
    }

}