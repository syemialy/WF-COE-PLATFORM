package com.partone.additionalutils;

import com.partone.FileUitl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class CollectLinks {

    private static String folderPath = "C:\\temp\\extracted_common_com";

    public static void main(String[] args) {
        File[] directories = new File(folderPath).listFiles();
        Arrays.stream(directories).filter(file -> !file.getName().contains("collected")).forEach(CollectLinks::handleBloknot);
    }

    public static void handleDirectory(File directory) {
        File[] bloknots = directory.listFiles();
        System.out.print("Handle directory: " + directory.toString());
        Arrays.stream(bloknots).forEach(CollectLinks::handleBloknot);
    }

    private static void handleBloknot(File file) {
        FileUitl fileUitl = new FileUitl();
        try {
            List<String> list = fileUitl.fileToList(file.getPath());
            for (String item: list) {
                item = item + System.lineSeparator();
                Files.write(Paths.get(new File(folderPath).getPath() + "\\collected.txt"), item.getBytes(), StandardOpenOption.APPEND);
            }
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

}
