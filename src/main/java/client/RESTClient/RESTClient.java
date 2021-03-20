package client.RESTClient;

import client.entity.Account;
import client.entity.Customer;
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
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static void updateCustomer(Customer customer) throws IOException {
        URL url = new URL("http://localhost:8080/spring_bank_war/api/account/");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection con = (HttpURLConnection) urlConnection;
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestMethod("PUT");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String userPass = "admin" + ":" + "admin";
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPass.getBytes());
        con.setRequestProperty("Authorization", basicAuth);
        Account account = new Account();
        String object = new Gson().toJson(account);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = object.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        Account result = null;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            result = new Gson().fromJson(response.toString(), Account.class);
            System.out.println(response.toString());

        }
    }


    public static Customer getCustomer(int idNumber, String password) {
        Customer customer=null;
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
            customer= new GsonBuilder()
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                        }
                    })
                    .create().fromJson(inputStreamReader,Customer.class);
            inputStreamReader.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return customer;
    }

    public static void getCustomers() throws IOException {
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
        List<Account> list1 = new Gson().fromJson(inputStreamReader, listType);
    }

    public static void deleteCustomer() throws IOException {
        URL url = new URL("http://localhost:8080/spring_bank_war/api/account/22");
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

        }
        System.out.println(response.toString());
    }

    public static void getTransactions(int accountNumber) throws IOException {
        URL url = new URL("http://localhost:8080/spring_bank_war/api/account/22");
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

        }
        System.out.println(response.toString());
    }


}