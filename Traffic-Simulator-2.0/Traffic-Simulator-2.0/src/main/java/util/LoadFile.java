package main.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadFile {
    public static void main(String[] args) throws Exception {
        //using class of nio file package
        Path filePath = Paths.get("C:\\Users\\Desktop\\test.txt");

        //converting to UTF 8
        Charset charset = StandardCharsets.UTF_8;

        //try with resource
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.format("I/O exception: ", ex);
        }
    }
}
