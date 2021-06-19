package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.entity.Transaction;
import client.utils.FrameSetup;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class AccountPanelFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private final Account account;
    private final List<Transaction> transactionList;
    private final Container container = getContentPane();
    private final  JLabel welcomeTextLabel = new JLabel();
    private final JLabel balanceInfo = new JLabel("Your balance: ");
    private final JLabel balance = new JLabel();
    private final JLabel accountNumberInfo = new JLabel("Your account number: ");
    private final JLabel accountNumber = new JLabel();
    private final JLabel transactionsInfo = new JLabel("Your last transactions");
    private final JButton createNewTransactionButton = new JButton("New transaction");
    private final JButton logoutBackButton = new JButton("Logout");
    private final JButton changePersonalDataButton = new JButton("Change personal data");
    private final JButton changePasswordButton = new JButton("Change password");
    private final JButton changePinNumberButton = new JButton("Change pin number");
    private final JPanel tableLabel = new JPanel();


    public AccountPanelFrame(Account account) {
        this.account = account;
        this.transactionList = RESTClient.getAccountTransactions(account);
        setFrameManager();
        setLayoutManager();
        setLocationAndSize();
        initTable();
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
        welcomeTextLabel.setBounds(250, 20, 400, 30);
        createNewTransactionButton.setBounds(450, 550, 200, 30);
        logoutBackButton.setBounds(150, 550, 200, 30);
        balanceInfo.setBounds(100, 120, 400, 30);
        balance.setBounds(250, 120, 400, 30);
        accountNumberInfo.setBounds(100, 170, 400, 30);
        accountNumber.setBounds(250, 170, 400, 30);
        tableLabel.setBounds(0, 250, 800, 300);
        transactionsInfo.setBounds(260, 220, 400, 30);
        changePersonalDataButton.setBounds(80, 60, 200, 30);
        changePasswordButton.setBounds(300, 60, 200, 30);
        changePinNumberButton.setBounds(520, 60, 200, 30);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        welcomeTextLabel.setText("Welcome " + account.getFirstName() + " " + account.getLastName());
        balance.setText(account.getBalance() + " zł");
        accountNumber.setText(account.getAccountNumber() + "");
        transactionsInfo.setFont(new Font("INK Free", Font.BOLD, 20));
        tableLabel.setBackground(COLOR);
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(createNewTransactionButton);
        container.add(logoutBackButton);
        container.add(balance);
        container.add(balanceInfo);
        container.add(accountNumber);
        container.add(accountNumberInfo);
        container.add(tableLabel);
        container.add(transactionsInfo);
        container.add(changePasswordButton);
        container.add(changePinNumberButton);
        container.add(changePersonalDataButton);
    }

    public void initTable() {
        String[] columnNames = {"Nr",
                "Type",
                "Amount",
                "Description",
                "Date"};
        Collections.sort(transactionList, (o1, o2) -> (int) (o2.getDate().getTime() - o1.getDate().getTime()));
        Object[][] data = new Object[transactionList.size()][5];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = i + 1;
                        break;
                    case 1:
                        data[i][j] = transactionList.get(i).getTransactionType();
                        break;
                    case 2:
                        data[i][j] = transactionList.get(i).getAmount() + " zł";
                        break;
                    case 3:
                        data[i][j] = transactionList.get(i).getDescription();
                        break;
                    case 4:
                        data[i][j] = transactionList.get(i).getDate();
                        break;
                }
            }
        }
        TableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(680, 240));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane pane = new JScrollPane(table);
        tableLabel.add(pane);
    }


    public void addActionEvent() {
        createNewTransactionButton.addActionListener(this);
        logoutBackButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        changePersonalDataButton.addActionListener(this);
        changePinNumberButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutBackButton) {
            new LoginJFrame();
            this.dispose();
        }
        if (e.getSource() == changePinNumberButton) {
            new ChangePinFrame(account);
            this.dispose();
        }
        if (e.getSource() == changePersonalDataButton) {
            new ChangeDataFrame(account);
            this.dispose();
        }
        if (e.getSource() == changePasswordButton) {
            new ChangePasswordFrame(account);
            this.dispose();
        }
        if (e.getSource() == createNewTransactionButton) {
            new TransactionFrame(account);
            this.dispose();
        }
    }
}
