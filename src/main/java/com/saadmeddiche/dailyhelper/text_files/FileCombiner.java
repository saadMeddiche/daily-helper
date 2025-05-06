package com.saadmeddiche.dailyhelper.text_files;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;

public class FileCombiner {
    public static void main(String[] args) {

        String inputFolderPath = "C:\\Users\\SMEDDICHE\\Downloads\\Ram Assistant errors";
        String outputFilePath = "C:\\Users\\SMEDDICHE\\Downloads\\Ram Assistant errors\\combined.txt";
        String filterString = "x-forwarded-host=";

        try {

            List<Path> matchingFiles = new ArrayList<>();

            Files.walk(Paths.get(inputFolderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {
                            String content = new String(Files.readAllBytes(path));
                            if (content.contains(filterString)) {
                                matchingFiles.add(path);
                                System.out.println("Found match in: " + path.getFileName());
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + path);
                            e.printStackTrace();
                        }
                    });

            if (matchingFiles.isEmpty()) {
                System.out.println("No files containing the filter string were found.");
                return;
            }


            BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));
            boolean isFirstFile = true;

            for (Path path : matchingFiles) {
                try {
                    String content = new String(Files.readAllBytes(path));

                    if (!isFirstFile) {
                        writer.write("\n----------\n");
                    } else {
                        isFirstFile = false;
                    }

                    writer.write(content);
                    System.out.println("Added to output: " + path.getFileName());
                } catch (IOException e) {
                    System.err.println("Error processing file: " + path);
                    e.printStackTrace();
                }
            }

            writer.close();
            System.out.println("Filtered files combined successfully to: " + outputFilePath);
            System.out.println("Total files combined: " + matchingFiles.size());

        } catch (IOException e) {
            System.err.println("An error occurred: ");
            e.printStackTrace();
        }
    }
}
