package com.aquatest;

import java.util.ArrayList;
import java.util.List;

import com.aquatest.model.FileInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileStatsReader {

    private List<String> getFilesInfo(String path) {

        List<String> result = new ArrayList<>();
        String command = "stat --printf=\"%n,%s,%F\\n\" " + path + "/*";

        System.out.println("Executing BASH command: " + command);
        Runtime r = Runtime.getRuntime();
        String[] commands = { "bash", "-c", command };

        try {
            Process p = r.exec(commands);
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = b.readLine()) != null) {
                // System.out.println(line);
                result.add(line);
            }
            b.close();
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return result;
    }

    public List<FileInfo> getFilesInfoList(String path) {

        List<String> rawFileStats = getFilesInfo(path);

        List<FileInfo> result = new ArrayList<>();

        for (String rawLine : rawFileStats) {
            String[] parts = rawLine.split(",");
            // check type
            if (parts[2].contains("regular")) {
                FileInfo fileInfo = new FileInfo(parts[0], Long.parseLong(parts[1]));
                result.add(fileInfo);
            }
        }

        // System.out.println(result.toString());
        return result;
    }

}
