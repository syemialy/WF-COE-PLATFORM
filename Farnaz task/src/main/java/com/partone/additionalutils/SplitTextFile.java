package com.partone.additionalutils;

import com.partone.FileUitl;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SplitTextFile {

    private static String pathToFile = "C:\\temp\\extracted_common_com\\collected_common_com.txt";

    public static void main(String... args) throws FileNotFoundException {
        SplitTextFile splitTextFile = new SplitTextFile();
        splitTextFile.split(pathToFile);

    }

    private void split(String pathToFile) throws FileNotFoundException {
        File file = new File(pathToFile);
        FileUitl fileUitl = new FileUitl();
        List<String> listLinks = fileUitl.fileToList(pathToFile);
        int divideCounter = 1_000_000;
        Map<Integer, List<Integer>> groups = IntStream.range(0, listLinks.size()).boxed()
                .collect(Collectors.groupingBy(s -> s/divideCounter));
        groups.entrySet().forEach(entry -> writeGroup(entry.getKey(), entry.getValue(), listLinks, file.getParentFile()));
    }

    private void writeGroup(Integer key, List<Integer> valueInGroup, List<String> listLinks, File folder){
        try {
            String path = folder.getPath() + "\\\\output_"+key;
            File file = new File(path);
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Integer index: valueInGroup) {
                writer.write(listLinks.get(index));
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*    public void writeFile() throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        for(String str: arr) {
            writer.write(str);
        }
        writer.close();
    }*/


}
