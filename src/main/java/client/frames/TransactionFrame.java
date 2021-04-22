package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.entity.Customer;
import client.enums.TransactionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Customer customer;
    private Account account;
    private boolean transactionCompleted = false;
    private TransactionType transactionType;
    Container container = getContentPane();
    JLabel welcomeTextLabel = new JLabel("New transaction ");
    JLabel transactionTypeLabel = new JLabel("Choose transaction type ");
    JLabel amountLabel = new JLabel("Amount: ");
    JLabel accountDestinationLabel = new JLabel("Enter a destination account number: ");
    JLabel informationMessage = new JLabel();
    JLabel dataChangedMassage = new JLabel();
    JLabel accountsPin = new JLabel("Pin number: ");
    JLabel descriptionLabel = new JLabel("Description: ");
    JPasswordField pinNumberField = new JPasswordField();
    JTextArea descriptionArea = new JTextArea();
    JTextField destinationField = new JTextField();
    JTextField amountField = new JTextField();
    JCheckBox showPin = new JCheckBox("Show Pin");
    JButton resetButton = new JButton("Reset");
    JButton submitButton = new JButton("Submit");
    JButton accountBackButton = new JButton("Back to account");
    JRadioButton withdrawButton = new JRadioButton("Withdraw");
    JRadioButton depositButton = new JRadioButton("Deposit");
    JRadioButton transferButton = new JRadioButton("Transfer");
    ButtonGroup typeGroup = new ButtonGroup();


    public TransactionFrame(Customer customer) {
        this.customer = customer;
        this.account = customer.getAccount();
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
        transactionTypeLabel.setBounds(250, 100, 300, 30);
        withdrawButton.setBounds(200, 130, 100, 30);
        depositButton.setBounds(350, 130, 100, 30);
        transferButton.setBounds(500, 130, 100, 30);
        amountLabel.setBounds(250, 170, 100, 30);
        amountField.setBounds(350, 170, 150, 30);
        accountDestinationLabel.setBounds(100, 310, 250, 30);
        destinationField.setBounds(350, 310, 150, 30);
        accountsPin.setBounds(235, 240, 150, 30);
        pinNumberField.setBounds(350, 240, 150, 30);
        showPin.setBounds(350, 270, 150, 30);
        descriptionLabel.setBounds(235, 380, 150, 30);
        descriptionArea.setBounds(350, 380, 150, 60);
        resetButton.setBounds(370, 460, 100, 30);
        submitButton.setBounds(450, 550, 200, 30);
        accountBackButton.setBounds(150, 550, 200, 30);
        informationMessage.setBounds(270, 550, 500, 100);
        dataChangedMassage.setBounds(220, 560, 700, 100);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        informationMessage.setVisible(false);
        dataChangedMassage.setVisible(false);
        dataChangedMassage.setFont(new Font("INK Free", Font.BOLD, 20));
        transactionTypeLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        depositButton.setBackground(COLOR);
        withdrawButton.setBackground(COLOR);
        transferButton.setBackground(COLOR);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        accountDestinationLabel.setVisible(false);
        destinationField.setVisible(false);
        descriptionLabel.setVisible(false);
        descriptionArea.setVisible(false);
        showPin.setBackground(COLOR);

    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(transactionTypeLabel);
        typeGroup.add(withdrawButton);
        typeGroup.add(depositButton);
        typeGroup.add(transferButton);
        container.add(withdrawButton);
        container.add(depositButton);
        container.add(transferButton);
        container.add(amountLabel);
        container.add(resetButton);
        container.add(submitButton);
        container.add(accountBackButton);
        container.add(dataChangedMassage);
        container.add(informationMessage);
        container.add(amountField);
        container.add(accountDestinationLabel);
        container.add(destinationField);
        container.add(accountsPin);
        container.add(pinNumberField);
        container.add(descriptionArea);
        container.add(descriptionLabel);
        container.add(showPin);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        submitButton.addActionListener(this);
        accountBackButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        transferButton.addActionListener(this);
        showPin.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton) {
            amountField.setText("");
            destinationField.setText("");
            pinNumberField.setText("");
            descriptionArea.setText("");
        }
        if (e.getSource() == showPin) {
            if (showPin.isSelected()) {
                pinNumberField.setEchoChar((char) 0);
            } else {
                pinNumberField.setEchoChar('*');
            }
        }
        if (withdrawButton.isSelected()) {
            transactionType = TransactionType.WITHDRAWAL;
            accountDestinationLabel.setVisible(false);
            destinationField.setVisible(false);
            descriptionLabel.setVisible(false);
            descriptionArea.setVisible(false);
        }
        if (depositButton.isSelected()) {
            transactionType = TransactionType.DEPOSIT;
            accountDestinationLabel.setVisible(false);
            destinationField.setVisible(false);
            descriptionLabel.setVisible(false);
            descriptionArea.setVisible(false);
        }
        if (transferButton.isSelected()) {
            transactionType = TransactionType.OUTGOING_TRANSFER;
            accountDestinationLabel.setVisible(true);
            destinationField.setVisible(true);
            descriptionLabel.setVisible(true);
            descriptionArea.setVisible(true);
        }
        if (e.getSource() == accountBackButton) {
            customer = RESTClient.getCustomer(customer.getId(), customer.getPassword());
            new AccountPanelFrame(customer);
            this.dispose();
        }
        if ((e.getSource() == submitButton) && !transactionCompleted) {
            boolean error = false;
            String text = "<html>";

            if (transactionType == null) {
                error = true;
                text = text + "Select a type of transaction.<br>";
            }
            if (pinNumberField.getText().isEmpty() || amountField.getText().isEmpty() ||
                    ((descriptionArea.getText().isEmpty() || destinationField.getText().isEmpty()) && transactionType == TransactionType.OUTGOING_TRANSFER)) {
                error = true;
                text = text + "Data fields must not be empty.<br>";
            }

            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setText(text + "</html>");
                informationMessage.setVisible(true);
            } else {
                String response = "";
                if (transactionType == TransactionType.OUTGOING_TRANSFER) {
                    response = RESTClient.transferMoney(account.getAccountNumber(), Integer.parseInt(pinNumberField.getText()),
                            Integer.parseInt(destinationField.getText()), Float.parseFloat(amountField.getText()), descriptionArea.getText());
                }
                if (transactionType == TransactionType.WITHDRAWAL) {
                    response = RESTClient.withdrawMoney(account.getAccountNumber(), Integer.parseInt(pinNumberField.getText()), Float.parseFloat(amountField.getText()));
                }
                if (transactionType == TransactionType.DEPOSIT) {
                    response = RESTClient.depositMoney(account.getAccountNumber(), Integer.parseInt(pinNumberField.getText()), Float.parseFloat(amountField.getText()));
                }

                if (response.startsWith("The money has been transferred") || response.startsWith("Withdrawal") || response.startsWith("Deposit")) {
                    informationMessage.setVisible(false);
                    dataChangedMassage.setVisible(true);
                    transactionCompleted = true;
                    dataChangedMassage.setText(response);
                    dataChangedMassage.setForeground(Color.green);
                } else {
                    if (response.startsWith("Account with number " + account.getAccountNumber() + " is not active.")) {
                        JOptionPane.showMessageDialog(this, "Your account is not active, please contact with the support.");
                    }
                    informationMessage.setVisible(true);
                    informationMessage.setText(response);
                    informationMessage.setForeground(Color.red);
                }

            }
        }
    }

}