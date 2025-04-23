package com.saadmeddiche.dailyhelper.text_files;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class LineModifier {

    private final static String NEW_LINE = "\'%s\',";

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\saadm\\Desktop\\txt.txt";
        String outputFilePath = "C:\\Users\\saadm\\Desktop\\txt-out.txt";

        try {
            Path inputPath = Paths.get(inputFilePath);
            Path outputPath = Paths.get(outputFilePath);

            try (BufferedReader reader = Files.newBufferedReader(inputPath);
                 BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(String.format(NEW_LINE, line));
                    writer.newLine();
                }
            }

            log.info("Semicolon added to the end of each line successfully. Output file: {}", outputFilePath);
        } catch (IOException e) {
            log.error("An error occurred: {}" , e.getMessage());
        }
    }
}

