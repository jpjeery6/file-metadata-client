package com.aquatest;

import com.aquatest.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aquatest.rest.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    private static final int MAX_NUM_OF_THREADS = 3;
    private static int CLIENT_ID = 10011;
    private static String serverUrl = "http://localhost:8080";
    private static String payload = "";
    private static String path = "/home/jp/aqua-test-dir";

    public static void main(String[] args) {

        // parsing args
        CliParser cliParser = new CliParser();
        List<String> parsedArgs = cliParser.parse(args);

        System.out.println(parsedArgs.toString());

        path = parsedArgs.get(0);
        serverUrl = parsedArgs.get(1);

        try {
            CLIENT_ID = Integer.parseInt(parsedArgs.get(2));
        } catch (Exception e) {
            // will go with default;
        }
        System.out.println("Arg values = " + path + "," + serverUrl + "," + CLIENT_ID);

        // Reading file stats
        FileStatsReader fileStatsReader = new FileStatsReader();
        List<FileInfo> fileInfoList = fileStatsReader.getFilesInfoList(path);
        // System.out.println(fileInfoList);

        // Object to json
        FileStats fileStats = new FileStats(CLIENT_ID, fileInfoList.size(), fileInfoList);
        ObjectMapper Obj = new ObjectMapper();

        try {
            payload = Obj.writeValueAsString(fileStats);
            // System.out.println("Generated payload: " + payload);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // http rest call
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
        for (int i = 0; i < 5; i++) { // simulating multi theaded API calls
            executorService.execute(new UpdateTask(serverUrl, payload));
        }
        // executorService.shutdown();

    }
}

class UpdateTask implements Runnable {

    String serverUrl;
    String payload;

    public UpdateTask(String serverUrl, String payload) {
        this.serverUrl = serverUrl;
        this.payload = payload;
    }

    public void run() {
        FileStatsService.update(serverUrl, payload);
    }

}
