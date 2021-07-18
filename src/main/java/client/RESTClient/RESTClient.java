package client.RESTClient;

import client.entity.Account;
import client.entity.Transaction;
import client.utils.HttpConnectionHelper;
import client.utils.Session;

import java.net.HttpURLConnection;
import java.util.List;


public class RESTClient {

    public static String withdrawMoney(int account, int pinNumber, float amount) {
        String url = "http://localhost:8080/api/withdraw/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }

    public static String depositMoney(int account, int pinNumber, float amount) {
        String url = "http://localhost:8080/api/deposit/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }


    public static String getResponseBody(int idNumber, String password) {
        String url = "http://localhost:8080/api/accounts/" + idNumber + "/" + password;
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }

    public static List<Transaction> getAccountTransactions(Account account) {
        String url = "http://localhost:8080/api/accounts/" + account.getId() + "/" + Session.password + "/transactions";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).openConnection().build();
        return HttpConnectionHelper.getListOfObjects(con, 1);
    }

    public static String changePin(int accountId, int oldPin, int newPin) {
        String url = "http://localhost:8080/api/accounts/changePin/" + accountId + "/" + oldPin + "/" + newPin;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnectionHelper.Builder().addUrl(url).setRequestMethod(requestType).openConnection().build();
        return HttpConnectionHelper.sendRequestAndReceiveResponse(con);
    }
}