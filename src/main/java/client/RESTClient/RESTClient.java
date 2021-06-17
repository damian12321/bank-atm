package client.RESTClient;

import client.entity.Account;
import client.entity.Transaction;
import client.exception.CustomExceptionHandler;
import client.utils.DateDeserializer;
import client.utils.HttpConnection;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RESTClient {

    public static int getFreeAccountNumber() {
        String url = "http://localhost:8080/api/accounts/number/";
        String requestType = "GET";
        int accountNumber = 0;
        StringBuilder response;
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                accountNumber = Integer.parseInt(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accountNumber;
    }

    public static String transferMoney(int fromAccount, int pinNumber, int destinationAccount, float amount, String description) {
        String result = null;
        String url = "http://localhost:8080/api/transfer/" + fromAccount + "/" + pinNumber + "/" + destinationAccount + "/" + amount + "/";
        String requestType = "POST";
        try {
            HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(description);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            BufferedReader br;
            InputStreamReader isr;
            if (con.getResponseCode() == 200) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (con.getResponseCode() == 200) {
                result = response.toString();

            } else {

                CustomExceptionHandler exception = new Gson().fromJson(response.toString(), CustomExceptionHandler.class);
                result = exception.getMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String withdrawMoney(int account, int pinNumber, float amount) {
        String result = null;
        String url = "http://localhost:8080/api/withdraw/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {
            BufferedReader br;
            InputStreamReader isr;
            if (con.getResponseCode() == 200) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (con.getResponseCode() == 200) {
                result = response.toString();
            } else {
                CustomExceptionHandler exception = new Gson().fromJson(response.toString(), CustomExceptionHandler.class);
                result = exception.getMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String depositMoney(int account, int pinNumber, float amount) {
        String result = null;
        String url = "http://localhost:8080/api/deposit/" + account + "/" + pinNumber + "/" + amount;
        String requestType = "POST";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {

            BufferedReader br;
            InputStreamReader isr;
            if (con.getResponseCode() == 200) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (con.getResponseCode() == 200) {
                result = response.toString();
            } else {
                CustomExceptionHandler exception = new Gson().fromJson(response.toString(), CustomExceptionHandler.class);
                result = exception.getMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Account getAccount(int idNumber, String password) {
        Account account = null;
        String url = "http://localhost:8080/api/accounts/" + idNumber + "/" + password;
        String requestType = "GET";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
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
        List<Account> list = null;
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            Type listType = new TypeToken<ArrayList<Account>>() {
            }.getType();
            list = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> list = null;
        String url = "http://localhost:8080/api/transactions/";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            Type listType = new TypeToken<ArrayList<Transaction>>() {
            }.getType();
            list = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<Transaction> getAccountTransactions(Account account) {
        List<Transaction> list = null;
        String url = "http://localhost:8080/api/accounts/"+account.getId()+"/"+account.getPassword()+"/transactions";
        String requestType = "GET";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            Type listType = new TypeToken<ArrayList<Transaction>>() {
            }.getType();
            list = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String delete(int id, String componentToDelete) {
        String result = "";
        String url = "http://localhost:8080/api/"+componentToDelete+"/"+id;
        String requestType = "DELETE";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useAdminPass().openConnection().build();
        try {
            StringBuilder response;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Account createAccount(Account account) {
        Account result = null;
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "POST";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(account);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(response.toString(), Account.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Account updateAccount(Account account) {
        Account result = null;
        String url = "http://localhost:8080/api/accounts/";
        String requestType = "PUT";
        HttpURLConnection con = new HttpConnection.Builder().addUrl(url).setRequestMethod(requestType).useCustomerPass().openConnection().build();
        try {
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(account);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(response.toString(), Account.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}