package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.enums.TransactionType;
import client.utils.FrameSetup;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private boolean transactionCompleted = false;
    private TransactionType transactionType;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("New transaction ");
    private final JLabel transactionTypeLabel = new JLabel("Choose transaction type ");
    private final JLabel amountLabel = new JLabel("Amount: ");
    private final JLabel accountDestinationLabel = new JLabel("Enter a destination account number: ");
    private final JLabel informationMessage = new JLabel();
    private final JLabel dataChangedMassage = new JLabel();
    private final JLabel accountsPin = new JLabel("Pin number: ");
    private final JPasswordField pinNumberField = new JPasswordField();
    private final JTextField amountField = new JTextField();
    private final JCheckBox showPin = new JCheckBox("Show Pin");
    private final JButton resetButton = new JButton("Reset");
    private final JButton submitButton = new JButton("Submit");
    private final JButton accountBackButton = new JButton("Back to account");
    private final JRadioButton withdrawButton = new JRadioButton("Withdraw");
    private final JRadioButton depositButton = new JRadioButton("Deposit");
    private final ButtonGroup typeGroup = new ButtonGroup();


    public TransactionFrame(Account account) {
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
        transactionTypeLabel.setBounds(250, 100, 300, 30);
        withdrawButton.setBounds(250, 130, 100, 30);
        depositButton.setBounds(400, 130, 100, 30);
        amountLabel.setBounds(250, 170, 100, 30);
        amountField.setBounds(350, 170, 150, 30);
        accountDestinationLabel.setBounds(100, 310, 250, 30);
        accountsPin.setBounds(235, 240, 150, 30);
        pinNumberField.setBounds(350, 240, 150, 30);
        showPin.setBounds(350, 270, 150, 30);
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
        accountDestinationLabel.setVisible(false);
        showPin.setBackground(COLOR);

    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(transactionTypeLabel);
        typeGroup.add(withdrawButton);
        typeGroup.add(depositButton);
        container.add(withdrawButton);
        container.add(depositButton);
        container.add(amountLabel);
        container.add(resetButton);
        container.add(submitButton);
        container.add(accountBackButton);
        container.add(dataChangedMassage);
        container.add(informationMessage);
        container.add(amountField);
        container.add(accountDestinationLabel);
        container.add(accountsPin);
        container.add(pinNumberField);
        container.add(showPin);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        submitButton.addActionListener(this);
        accountBackButton.addActionListener(this);
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        showPin.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton) {
            amountField.setText("");
            pinNumberField.setText("");

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
        }
        if (depositButton.isSelected()) {
            transactionType = TransactionType.DEPOSIT;
            accountDestinationLabel.setVisible(false);
        }
        if (e.getSource() == accountBackButton) {
            account = RESTClient.getAccount(account.getId(), account.getPassword());
            if(account==null)
            {
                new LoginJFrame();
            }else {
                new AccountPanelFrame(account);
            }
            this.dispose();
        }
        if ((e.getSource() == submitButton) && !transactionCompleted) {
            boolean error = false;
            String text = "<html>";

            if (transactionType == null) {
                error = true;
                text = text + "Select a type of transaction.<br>";
            }
            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setText(text + "</html>");
                informationMessage.setVisible(true);
            } else {
                String response = "";
                try {
                    Float.parseFloat(amountField.getText());
                } catch (NumberFormatException nfe) {
                    response = "Amount field is incorrect.";
                }
                if ((transactionType == TransactionType.WITHDRAWAL) && response.isEmpty()) {
                    response = RESTClient.withdrawMoney(account.getAccountNumber(), Integer.parseInt(pinNumberField.getText()), Float.parseFloat(amountField.getText()));
                }
                if ((transactionType == TransactionType.DEPOSIT) && response.isEmpty()) {
                    response = RESTClient.depositMoney(account.getAccountNumber(), Integer.parseInt(pinNumberField.getText()), Float.parseFloat(amountField.getText()));
                }

                if (response.startsWith("Withdrawal") || response.startsWith("Deposit")) {
                    informationMessage.setVisible(false);
                    dataChangedMassage.setVisible(true);
                    transactionCompleted = true;
                    dataChangedMassage.setText(response);
                    dataChangedMassage.setForeground(Color.green);
                } else {
                    if (response.equals("Account with number " + account.getAccountNumber() + " is not active.")) {
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