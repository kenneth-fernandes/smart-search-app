package com.smartsearchapp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;

public class FileConversionUtil {

    public static File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = File.createTempFile("audio", ".wav");
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    public static void deleteFile(File file) throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    public static String fetchTranscriptionText(String transcriptUrl) throws IOException {
        URL url = new URL(transcriptUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder transcriptionText = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                transcriptionText.append(scanner.nextLine());
            }
        }
        return transcriptionText.toString();
    }
}