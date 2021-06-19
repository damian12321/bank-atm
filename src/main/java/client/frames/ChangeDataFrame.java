package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.utils.FrameSetup;
import client.utils.JTextFieldPlaceholder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeDataFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private boolean dataChanged = false;
    private final boolean isAdmin;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Change personal data ");
    private final JLabel customersName = new JLabel("First name: ");
    private final JLabel customersLastName = new JLabel("Last name: ");
    private final JLabel informationMessage = new JLabel();
    private final JLabel dataChangedMassage = new JLabel();
    private final JTextFieldPlaceholder customersNameField = new JTextFieldPlaceholder();
    private final JTextFieldPlaceholder customersLastNameField = new JTextFieldPlaceholder();
    private final JButton resetButton = new JButton("Reset");
    private final JButton submitButton = new JButton("Submit");
    private final JButton accountBackButton = new JButton("Back to account");


    public ChangeDataFrame(Account account) {
        this.account = account;
        isAdmin = false;
        setFrameManager();
        setLayoutManager();
        setLocationAndSize();
        setProperties();
        addComponentsToContainer();
        addActionEvent();
    }

    public ChangeDataFrame(Account account, boolean isAdmin) {
        this.account = account;
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
        customersName.setBounds(250, 100, 100, 30);
        customersLastName.setBounds(250, 170, 100, 30);
        customersNameField.setBounds(350, 100, 150, 30);
        customersLastNameField.setBounds(350, 170, 150, 30);
        resetButton.setBounds(370, 210, 100, 30);
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
        customersNameField.setText(account.getFirstName());
        customersLastNameField.setText(account.getLastName());
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(customersName);
        container.add(customersLastName);
        container.add(resetButton);
        container.add(submitButton);
        container.add(accountBackButton);
        container.add(dataChangedMassage);
        container.add(informationMessage);
        container.add(customersNameField);
        container.add(customersLastNameField);
    }

    public void addActionEvent() {
        resetButton.addActionListener(this);
        submitButton.addActionListener(this);
        accountBackButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            customersNameField.setText("");
            customersLastNameField.setText("");
        }
        if (e.getSource() == accountBackButton) {
            if (!isAdmin) {
                new AccountPanelFrame(account);
            } else {
                new AdminFrame();
            }
            this.dispose();
        }
        if ((e.getSource() == submitButton) && !dataChanged) {
            boolean error = false;
            String text = "<html>";

            if (customersLastNameField.getText().isEmpty() || customersNameField.getText().isEmpty()) {
                error = true;
                text = text + "Data fields must not be empty.<br>";
            }
            if (error) {
                informationMessage.setForeground(Color.red);
                informationMessage.setText(text);
                informationMessage.setText(text + "</html>");
                informationMessage.setVisible(true);
            } else {
                account.setFirstName(customersNameField.getText());
                account.setLastName(customersLastNameField.getText());
                Account response = RESTClient.updateAccount(account);

                if (response.getId() != 0) {
                    account = response;
                    informationMessage.setVisible(false);
                    dataChangedMassage.setVisible(true);
                    dataChanged = true;
                    dataChangedMassage.setText("<html>Your personal data has been changed.</html>");
                    dataChangedMassage.setForeground(Color.green);
                } else {
                    informationMessage.setVisible(true);
                    informationMessage.setText("Something went wrong.");
                    informationMessage.setForeground(Color.red);
                }

            }
        }
    }

}