package client.frames;

import client.RESTClient.RESTClient;
import client.entity.Account;
import client.entity.Transaction;
import client.utils.FrameSetup;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class AdminFrame extends JFrame implements ActionListener {
    private static final Color COLOR = new Color(227, 227, 227);
    private Account account;
    private final List<Transaction> transactionList;
    private final List<Account> accountList;
    private int number;
    private final Container container = getContentPane();
    private final JLabel welcomeTextLabel = new JLabel("Admin panel");
    private final JLabel listLabel = new JLabel("Last transactions");
    private final JLabel tableViewLabel = new JLabel("Select a table view ");
    private final JLabel numberLabel = new JLabel();
    private final JButton logoutBackButton = new JButton("Logout");
    private final JButton changePersonalDataButton = new JButton("Change personal data");
    private final JButton changePasswordButton = new JButton("Change password");
    private final JButton changePinNumberButton = new JButton("Change pin number");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton blockUnblockButton = new JButton();
    private JPanel transactionsTableLabel;
    private JPanel accountsTableLabel;
    private JPanel accountsPersonalInfoTableLabel;
    private final JRadioButton transactionsButton = new JRadioButton("Transactions");
    private final JRadioButton accountsButton = new JRadioButton("Account info");
    private final JRadioButton accountsPersonalInfoButton = new JRadioButton("Personal info");
    private final ButtonGroup typeGroup = new ButtonGroup();
    private final JComboBox<Integer> accountsBox = new JComboBox<>();
    private final JComboBox<Integer> transactionsBox = new JComboBox<>();
    private final JComboBox<Integer> accountsPersonalInfoBox = new JComboBox<>();


    public AdminFrame() {
        accountList = RESTClient.getAllAccounts();
        transactionList = RESTClient.getAllTransactions();
        setFrameManager();
        setLayoutManager();
        initTransactionsTable();
        initAccountsTable();
        initaccountsPersonalInfoTable();
        setLocationAndSize();
        initComboBoxes();
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
        welcomeTextLabel.setBounds(325, 20, 400, 30);
        logoutBackButton.setBounds(300, 575, 200, 30);
        accountsTableLabel.setBounds(0, 150, 800, 300);
        transactionsTableLabel.setBounds(0, 150, 800, 300);
        accountsPersonalInfoTableLabel.setBounds(0, 150, 800, 300);
        listLabel.setBounds(325, 120, 400, 30);
        changePersonalDataButton.setBounds(80, 515, 200, 30);
        changePasswordButton.setBounds(520, 515, 200, 30);
        changePinNumberButton.setBounds(520, 515, 200, 30);
        tableViewLabel.setBounds(325, 60, 300, 30);
        transactionsButton.setBounds(200, 90, 100, 30);
        accountsButton.setBounds(350, 90, 100, 30);
        accountsPersonalInfoButton.setBounds(500, 90, 100, 30);
        accountsPersonalInfoBox.setBounds(350, 470, 100, 30);
        accountsBox.setBounds(350, 470, 100, 30);
        transactionsBox.setBounds(350, 470, 100, 30);
        numberLabel.setBounds(325, 440, 300, 30);
        deleteButton.setBounds(300, 515, 200, 30);
        blockUnblockButton.setBounds(300, 515, 200, 30);
    }

    public void setProperties() {
        welcomeTextLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        listLabel.setFont(new Font("INK Free", Font.BOLD, 20));
        accountsButton.setBackground(COLOR);
        transactionsButton.setBackground(COLOR);
        accountsPersonalInfoButton.setBackground(COLOR);
        accountsTableLabel.setBackground(COLOR);
        accountsPersonalInfoTableLabel.setBackground(COLOR);
        transactionsTableLabel.setBackground(COLOR);
        listLabel.setVisible(false);
        transactionsTableLabel.setVisible(false);
        accountsTableLabel.setVisible(false);
        accountsPersonalInfoTableLabel.setVisible(false);
        numberLabel.setVisible(false);
        deleteButton.setVisible(false);
        changePasswordButton.setVisible(false);
        changePinNumberButton.setVisible(false);
        changePersonalDataButton.setVisible(false);
        transactionsBox.setVisible(false);
        accountsBox.setVisible(false);
        accountsPersonalInfoBox.setVisible(false);
        blockUnblockButton.setVisible(false);
    }

    public void addComponentsToContainer() {
        container.add(welcomeTextLabel);
        container.add(logoutBackButton);
        container.add(accountsTableLabel);
        container.add(listLabel);
        container.add(changePasswordButton);
        container.add(changePinNumberButton);
        container.add(changePersonalDataButton);
        container.add(tableViewLabel);
        typeGroup.add(transactionsButton);
        typeGroup.add(accountsButton);
        typeGroup.add(accountsPersonalInfoButton);
        container.add(transactionsButton);
        container.add(accountsButton);
        container.add(accountsPersonalInfoButton);
        container.add(transactionsTableLabel);
        container.add(accountsPersonalInfoTableLabel);
        container.add(accountsPersonalInfoBox);
        container.add(accountsBox);
        container.add(transactionsBox);
        container.add(numberLabel);
        container.add(deleteButton);
        container.add(blockUnblockButton);
    }

    public void initTransactionsTable() {
        transactionsTableLabel = new JPanel();
        String[] columnNames = {"Nr",
                "Type",
                "Amount",
                "Description",
                "Date"};
        Collections.sort(transactionList, (o1, o2) -> (int) (o2.getDate().getTime() - o1.getDate().getTime() + o2.getId() - o1.getId()));
        Object[][] data = new Object[transactionList.size()][5];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = transactionList.get(i).getId();
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
        transactionsTableLabel.add(pane);
    }

    public void initAccountsTable() {
        accountsTableLabel = new JPanel();
        String[] columnNames = {"Nr",
                "Account number",
                "Balance",
                "Pin number",
                "Active"};
        Object[][] data = new Object[accountList.size()][5];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = accountList.get(i).getId();
                        break;
                    case 1:
                        data[i][j] = accountList.get(i).getAccountNumber();
                        break;
                    case 2:
                        data[i][j] = accountList.get(i).getBalance();
                        break;
                    case 3:
                        data[i][j] = accountList.get(i).getPinNumber();
                        break;
                    case 4:
                        data[i][j] = accountList.get(i).getIsActive();
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

        table.getColumnModel().getColumn(0).setPreferredWidth(136);
        table.getColumnModel().getColumn(1).setPreferredWidth(136);
        table.getColumnModel().getColumn(2).setPreferredWidth(136);
        table.getColumnModel().getColumn(3).setPreferredWidth(136);
        table.getColumnModel().getColumn(4).setPreferredWidth(136);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane pane = new JScrollPane(table);
        accountsTableLabel.add(pane);
    }

    public void initaccountsPersonalInfoTable() {
        accountsPersonalInfoTableLabel = new JPanel();
        String[] columnNames = {"Nr",
                "Name",
                "Last name",
                "Account number",
                "Password"
        };
        Object[][] data = new Object[accountList.size()][5];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = accountList.get(i).getId();
                        break;
                    case 1:
                        data[i][j] = accountList.get(i).getFirstName();
                        break;
                    case 2:
                        data[i][j] = accountList.get(i).getLastName();
                        break;
                    case 3:
                        data[i][j] = accountList.get(i).getAccountNumber();
                        break;
                    case 4:
                        data[i][j] = accountList.get(i).getPassword();
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


        table.getColumnModel().getColumn(0).setPreferredWidth(136);
        table.getColumnModel().getColumn(1).setPreferredWidth(136);
        table.getColumnModel().getColumn(2).setPreferredWidth(136);
        table.getColumnModel().getColumn(3).setPreferredWidth(136);
        table.getColumnModel().getColumn(4).setPreferredWidth(136);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane pane = new JScrollPane(table);
        accountsPersonalInfoTableLabel.add(pane);
    }

    public void initComboBoxes() {
        for (Account account : accountList) {
            accountsBox.addItem(account.getId());
            accountsPersonalInfoBox.addItem(account.getId());
        }
        for (Transaction transaction : transactionList) {
            transactionsBox.addItem(transaction.getId());
        }
    }


    public void addActionEvent() {
        logoutBackButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        changePersonalDataButton.addActionListener(this);
        changePinNumberButton.addActionListener(this);
        transactionsButton.addActionListener(this);
        accountsButton.addActionListener(this);
        accountsPersonalInfoButton.addActionListener(this);
        accountsBox.addActionListener(this);
        transactionsBox.addActionListener(this);
        accountsPersonalInfoBox.addActionListener(this);
        deleteButton.addActionListener(this);
        blockUnblockButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (transactionsButton.isSelected()) {
            listLabel.setVisible(true);
            listLabel.setText("Last transactions");
            numberLabel.setText("Choose a transaction's number");
            transactionsTableLabel.setVisible(true);
            accountsTableLabel.setVisible(false);
            accountsPersonalInfoTableLabel.setVisible(false);
            numberLabel.setVisible(true);
            transactionsBox.setVisible(true);
            accountsBox.setVisible(false);
            accountsPersonalInfoBox.setVisible(false);
            deleteButton.setVisible(true);
            changePersonalDataButton.setVisible(false);
            changePinNumberButton.setVisible(false);
            changePasswordButton.setVisible(false);
            blockUnblockButton.setVisible(false);
            number = transactionsBox.getSelectedIndex();
        }
        if (accountsButton.isSelected()) {
            listLabel.setVisible(true);
            listLabel.setText("Accounts list");
            numberLabel.setText("Choose an account's id number");
            transactionsTableLabel.setVisible(false);
            accountsTableLabel.setVisible(true);
            accountsPersonalInfoTableLabel.setVisible(false);
            numberLabel.setVisible(true);
            transactionsBox.setVisible(false);
            accountsBox.setVisible(true);
            accountsPersonalInfoBox.setVisible(false);
            deleteButton.setVisible(false);
            changePersonalDataButton.setVisible(false);
            changePinNumberButton.setVisible(true);
            changePasswordButton.setVisible(false);
            blockUnblockButton.setVisible(true);
            number = accountsBox.getSelectedIndex();
            boolean tempBoolean = accountList.get(number).getIsActive();
            if (tempBoolean) {
                blockUnblockButton.setText("Block");
            } else {
                blockUnblockButton.setText("Unblock");
            }

        }
        if (accountsPersonalInfoButton.isSelected()) {
            listLabel.setVisible(true);
            listLabel.setText("Accounts list");
            numberLabel.setText("Choose an account's id number");
            transactionsTableLabel.setVisible(false);
            accountsTableLabel.setVisible(false);
            accountsPersonalInfoTableLabel.setVisible(true);
            numberLabel.setVisible(true);
            transactionsBox.setVisible(false);
            accountsBox.setVisible(false);
            accountsPersonalInfoBox.setVisible(true);
            deleteButton.setVisible(true);
            changePersonalDataButton.setVisible(true);
            changePinNumberButton.setVisible(false);
            changePasswordButton.setVisible(true);
            blockUnblockButton.setVisible(false);
            number = accountsPersonalInfoBox.getSelectedIndex();
        }
        if (e.getSource() == logoutBackButton) {
            new LoginJFrame();
            this.dispose();
        }
        if (e.getSource() == blockUnblockButton) {
            boolean tempBoolean = accountList.get(number).getIsActive();
            account = accountList.get(number);
            if (tempBoolean) {
                account.setIsActive(false);
            } else {
                account.setIsActive(true);
            }
            RESTClient.updateAccount(account);
            new AdminFrame();
            this.dispose();
        }
        if (e.getSource() == changePinNumberButton) {
            String password1 = accountList.get(number).getPassword();
            int id = accountList.get(number).getId();
            account = RESTClient.getAccount(id, password1);
            new ChangePinFrame(account, true);
            this.dispose();
        }
        if (e.getSource() == changePersonalDataButton) {
            String password1 = accountList.get(number).getPassword();
            int id = accountList.get(number).getId();
            account = RESTClient.getAccount(id, password1);
            new ChangeDataFrame(account, true);
            this.dispose();
        }
        if (e.getSource() == changePasswordButton) {
            String password1 = account.getPassword();
            int id = account.getId();
            account = RESTClient.getAccount(id, password1);
            new ChangePasswordFrame(account, true);
            this.dispose();
        }
        if (e.getSource() == deleteButton) {
            if (JOptionPane.showConfirmDialog(this, "Are you sure?", "WARNING",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (accountsPersonalInfoButton.isSelected()) {
                    int id = 0;
                    id = accountList.get(number).getId();
                    String response = RESTClient.delete(id, "accounts");

                    if (response.endsWith("has been deleted.")) {
                        new AdminFrame();
                        this.dispose();
                    }
                }
                if (transactionsButton.isSelected()) {
                    int id = 0;
                    id = transactionList.get(number).getId();
                    String response = RESTClient.delete(id, "transactions");

                    if (response.endsWith("has been deleted.")) {
                        new AdminFrame();
                        this.dispose();
                    }
                }
            }
        }
    }
}
