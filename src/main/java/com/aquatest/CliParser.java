package com.aquatest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliParser {

    String inputPath, serverUrl, clientId;

    public List<String> parse(String[] args) {
        Options options = new Options();

        Option option1 = Option.builder("p").required(true).longOpt("path").desc("provide absolute filesystem path")
                .hasArg().build();
        Option option2 = Option.builder("s").required(true).longOpt("serverUrl")
                .desc("provide file statistics server address").hasArg().build();
        Option option3 = Option.builder("i").required(false).longOpt("clientId")
                .desc("provide a unique integer client id (optional, default=10011)").hasArg().build();

        options.addOption(option1);
        options.addOption(option2);
        options.addOption(option3);

        ArrayList<String> parsedArgsList = new ArrayList<>();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("p")) {
                inputPath = cmd.getOptionValue("p");
                // System.out.println(inputPath);
                // check format if it is valid
                validatePath(inputPath);
            }
            if (cmd.hasOption("s")) {
                serverUrl = cmd.getOptionValue("s");
                // System.out.println(serverUrl);
                // check format
                validateUrl(serverUrl);
            }
            if (cmd.hasOption("i")) {
                clientId = cmd.getOptionValue("i");
                // System.out.println(clientId);
                validateId(clientId);
            }
            // System.out.println("here outside");
            parsedArgsList.add(inputPath);
            parsedArgsList.add(serverUrl);
            parsedArgsList.add(clientId);
            return parsedArgsList;

        } catch (ParseException p) {
            System.out.println(p.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <jar_name> ", options);
            System.exit(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <jar_name> ", options);
            System.exit(1);
        }

        return null;
    }

    private void validatePath(String path) throws ParseException {

        File file = new File(path);
        if (!file.exists()) {
            throw new ParseException("path doesnot exist");
        }

        if (file.exists() && file.isFile()) {
            throw new ParseException("Provided input is a file, please provide a directory");
        }
        if (file.isDirectory() && !path.endsWith("/")) {
            inputPath += "/";
        }

    }

    private void validateUrl(String url) throws ParseException {

        try {
            new URL(url).toURI();

        } catch (Exception e) {
            throw new ParseException("invalid url");
        }

    }

    private void validateId(String id) throws ParseException {

        try {
            Integer.parseInt(id);

        } catch (Exception e) {
            throw new ParseException("invalid client id, provide a integer id");
        }

    }

}
