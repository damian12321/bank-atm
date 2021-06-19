package client.utils;

import client.entity.Account;
import client.entity.Transaction;
import client.exception.CustomExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpConnectionHelper {
    public static final class Builder {
        private String url;
        private String pass;
        private String basicAuth;
        private HttpURLConnection con;
        private String requestMethod;

        public Builder addUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder openConnection() {
            URLConnection urlConnection;
            try {
                urlConnection = new URL(url).openConnection();
                this.con = (HttpURLConnection) urlConnection;
                this.con.setRequestMethod(requestMethod);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.con.setRequestProperty("Content-Type", "application/json; utf-8");
            this.con.setRequestProperty("Accept", "application/json");
            this.con.setRequestProperty("Authorization", basicAuth);
            this.con.setDoOutput(true);
            return this;
        }

        public Builder setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Builder useCustomerPass() {
            this.pass = "customer" + ":" + "customer";
            this.basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(pass.getBytes());
            return this;
        }

        public Builder useAdminPass() {
            this.pass = "admin" + ":" + "admin";
            this.basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(pass.getBytes());
            return this;
        }

        public HttpURLConnection build() {
            return this.con;
        }


    }

    public static String sendRequestAndReceiveResponse(HttpURLConnection httpURLConnection) {
        String result = "";
        try {
            BufferedReader br;
            InputStreamReader isr;
            if (httpURLConnection.getResponseCode() == 200) {
                isr = new InputStreamReader(httpURLConnection.getInputStream());
            } else {
                isr = new InputStreamReader(httpURLConnection.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            if (httpURLConnection.getResponseCode() == 200) {
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

    public static String sendDataAndReceiveResponse(HttpURLConnection httpURLConnection, Object objectToSend) {
        String result = "";
        try {
            String object = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create().toJson(objectToSend);
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = object.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            BufferedReader br;
            InputStreamReader isr;
            if (httpURLConnection.getResponseCode() == 200 || httpURLConnection.getResponseCode() == 201) {
                isr = new InputStreamReader(httpURLConnection.getInputStream());
            } else {
                isr = new InputStreamReader(httpURLConnection.getErrorStream());
            }
            br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (httpURLConnection.getResponseCode() == 200 || httpURLConnection.getResponseCode() == 201) {
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

    public static List getListOfObjects(HttpURLConnection httpURLConnection, int typeOfObject) {//typeOfObject,0=account,other=transaction
        Type listType;
        List list;
        if (typeOfObject == 0) {
            list = new ArrayList<Account>();
            listType = new TypeToken<ArrayList<Account>>() {
            }.getType();
        } else {
            list = new ArrayList<Transaction>();
            listType = new TypeToken<ArrayList<Transaction>>() {
            }.getType();
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            list = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(inputStreamReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
