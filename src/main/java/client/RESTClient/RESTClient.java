package client.RESTClient;

import client.entity.Account;
import client.entity.Customer;
import client.entity.Transaction;
import client.exception.CustomExceptionHandler;
import client.utils.DateDeserializer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RESTClient {

    public static int getFreeAccountNumber() {
        int accountNumber = 0;
        StringBuilder response = null;
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/account/number/");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestMethod("GET");
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String responseLine = null;
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

    public static Customer createCustomer(Customer customer) {
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/customer");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);

            String object = new Gson().toJson(customer);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                customer = new Gson().fromJson(response.toString(), Customer.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static Customer updateCustomer(Customer customer) {
        Customer result = null;
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/customer");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(customer);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(response.toString(), Customer.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String transferMoney(int fromAccount, int pinNumber, int destinationAccount, float amount, String description) {
        String result = null;
        try {
            String urlString = "http://localhost:8080/spring_bank_war/api/transfer/" + fromAccount + "/" + pinNumber + "/" + destinationAccount + "/" + amount + "/";
            System.out.println(urlString);
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(description);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = object.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            BufferedReader br = null;
            InputStreamReader isr = null;
            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                result = response.toString();
                System.out.println(response.toString());
            } else {
                System.out.println(response.toString());
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
        try {
            String urlString = "http://localhost:8080/spring_bank_war/api/withdraw/" + account + "/" + pinNumber + "/" + amount;
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);

            BufferedReader br = null;
            InputStreamReader isr = null;
            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
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
        try {
            String urlString = "http://localhost:8080/spring_bank_war/api/deposit/" + account + "/" + pinNumber + "/" + amount;
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);

            BufferedReader br = null;
            InputStreamReader isr = null;
            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                isr = new InputStreamReader(con.getInputStream());
            } else {
                isr = new InputStreamReader(con.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
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


    public static Customer getCustomer(int idNumber, String password) {
        Customer customer = null;
        try {
            String urlString = "http://localhost:8080/spring_bank_war/api/customer/" + idNumber + "/" + password;
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestMethod("GET");
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            customer = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, Customer.class);
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> list = null;
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/customer/");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestMethod("GET");
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            Type listType = new TypeToken<ArrayList<Customer>>() {
            }.getType();
            list = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Account> getAllAccounts() {
        List<Account> list = null;
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/account/");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestMethod("GET");
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
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
        try {
            URL url = new URL("http://localhost:8080/spring_bank_war/api/transaction/");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestMethod("GET");
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
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
        try {
            String urlString = "http://localhost:8080/spring_bank_war/api/" + componentToDelete + "/" + id;
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) urlConnection;
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String userPass = "admin" + ":" + "admin";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            StringBuilder response = null;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String responseLine = null;
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

}