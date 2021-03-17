package client.entity;
import client.enums.TransactionType;

import java.util.Date;
import java.util.Objects;


public class Transaction {
    private int id;
    private TransactionType transactionType;
    private float amount;
    private Date date;
    private String description;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, float amount, Date date, String description) {
        this.transactionType = transactionType;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Transaction(int id, TransactionType transactionType, float amount, Date date, String description) {
        this.id = id;
        this.transactionType = transactionType;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Float.compare(that.amount, amount) == 0 && transactionType == that.transactionType && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, amount, description);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}