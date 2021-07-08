package com.aquatest.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// class UpdateTask implements Runnable {

//     String serverUrl;
//     String payload;

//     public UpdateTask(String serverUrl, String payload) {
//         this.serverUrl = serverUrl;
//         this.payload = payload;
//     }

//     public void run() {
//         FileStatsService.update(serverUrl, payload);
//     }
// }

// class GetTask implements Runnable {

//     String serverUrl;

//     public GetTask(String serverUrl) {
//         this.serverUrl = serverUrl;
//     }

//     public void run() {
//         FileStatsService.get(serverUrl);
//     }
// }

public class FileStatsService {

    public static void update(String serverUrl, String payload) {
        String PostEndpoint = serverUrl + "/uploadfilestats";
        // System.out.println("Thread " + Thread.currentThread().getName());

        HttpResponse outPutReq = sendHttpReq(PostEndpoint, "POST", payload);
        // System.out.println("Output body: " + outPutReq.getBody());
        // System.out.println("Output response code: " + outPutReq.getResponseCode());
        if (outPutReq.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            // System.out.println(Thread.currentThread().getName() + " Request successfully
            // completed");
            return;
        } else {
            // System.out.println(Thread.currentThread().getName() + " Error requesting to
            // server");
            return;
        }

    }

    private static HttpResponse sendHttpReq(String serverUrl, String method, String body) {
        System.out.println(
                Thread.currentThread().getName() + " sendHttpReq " + " Calling " + method + " at " + serverUrl);
        try {
            URL obj = new URL(serverUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

            if (body != null) {
                con.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
                bw.write(body);
                bw.flush();
                bw.close();
            }

            Thread.sleep(1000);

            // System.out.println(
            // Thread.currentThread().getName() + " sendHttpReq " + " Response Code : " +
            // con.getResponseCode());

            BufferedReader in = null;
            if (con.getErrorStream() != null) {
                // System.out.println("reading con.getErrorStream()...");
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append(System.lineSeparator());
            }
            in.close();
            String out = response.toString().trim();

            // output
            System.out.println(Thread.currentThread().getName() + " sendHttpReq " + " output: " + out);

            con.disconnect();

            HttpResponse responseObj = new HttpResponse(out, con.getHeaderFields(), con.getResponseCode());

            return responseObj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
