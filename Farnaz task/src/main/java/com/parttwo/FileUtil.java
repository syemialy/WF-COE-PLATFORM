package com.parttwo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    public List<String> readFile(String path) {
        List<String> result = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(result::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

