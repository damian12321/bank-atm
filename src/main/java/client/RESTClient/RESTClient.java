package client.RESTClient;

import client.entity.Account;
import client.entity.Transaction;
import client.utils.DateDeserializer;
import client.utils.HttpConnectionHelper;
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.List;


public class RESTClient {

    public static int getFreeAccountNumber() {
        String url = "http://localhost:8080/api/accounts/number/";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        return Integer.parseInt(HttpConnectionHelper.sendRequestAndReceiveResponse(con));
    }

    public static String transferMoney(int fromAccount, int pinNumber, int destinationAccount, float amount, String description) {
        String url = "http://localhost:8080/api/transfer/" + fromAccount + "/" + pinNumber + "/" + destinationAccount + "/" + amount + "/";
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        return HttpConnectionHelper.sendDataAndReceiveResponse(con, description);
    }

    public static String withdrawMoney(int account, int pinNumber, float amount) {
        String url = "http://localhost:8080/api/withdraw/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }

    public static String depositMoney(int account, int pinNumber, float amount) {
        String url = "http://localhost:8080/api/deposit/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }


    public static Account getAccount(int idNumber, String password) {
        Account account = null;
        String url = "http://localhost:8080/api/accounts/" + idNumber + "/" + password;
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            account = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, Account.class);
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static List<Account> getAllAccounts() {
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        return HttpConnectionHelper.getListOfObjects(con,0);
    }

    public static List<Transaction> getAllTransactions() {
        String url = "http://localhost:8080/api/transactions/";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        return HttpConnectionHelper.getListOfObjects(con,1);
    }

    public static List<Transaction> getAccountTransactions(Account account) {
        String url = "http://localhost:8080/api/accounts/" + account.getId() + "/" + account.getPassword() + "/transactions";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        return HttpConnectionHelper.getListOfObjects(con,1);
    }

    public static String delete(int id, String componentToDelete) {
        String url = "http://localhost:8080/api/" + componentToDelete + "/" + id;
        String requestType = "DELETE";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }

    public static Account createAccount(Account account) {
        Account result;
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        String response = HttpConnectionHelper.sendDataAndReceiveResponse(con, account);
        result = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(response, Account.class);
        return result;
    }

    public static Account updateAccount(Account account) {
        Account result;
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "PUT";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        String response = HttpConnectionHelper.sendDataAndReceiveResponse(con, account);
        result = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(response, Account.class);
        return result;

    }
}