package com.example.cipraaiapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHandler {
    public interface GetResponseCallback {
        void onSuccess(String response);
        void onFailure(Exception e);
    }

    public static String buildUrl(final String email, final String password) {
        final String BASE_URL = "https://api.cipra.ai:5000/signin?email=%s&password=%s";
        return String.format(BASE_URL, email, password);
    }
    public static void sendGetRequest(final String requestUrl, final GetResponseCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Network",requestUrl);
                    URL url = new URL(requestUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        callback.onSuccess(response.toString());
                    } else {
                        callback.onFailure(new Exception("Failed to fetch data"));
                    }
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }
        });

        thread.start();
    }
}
