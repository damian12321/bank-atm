package client.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnection {
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

}
