package com;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortDataset {

    public int divideCounter = 500;
    public int startIndex = 54;

    public static void main(String ...args) throws IOException {
        SortDataset sortDataset = new SortDataset();
        sortDataset.sort("C:\\temp\\task_wf_webpages\\extracted_common_com");

    }


    public void sort(String path) throws IOException {
        Path myPath = Paths.get(path);
        Path parentPath = myPath.getParent();
        List<Path> listFolders = Files.list(myPath).collect(Collectors.toList());
        Map<Integer, List<Integer>> groups = IntStream.range(0, listFolders.size()).boxed()
                .collect(Collectors.groupingBy(s -> s/divideCounter));
        groups.entrySet().forEach(entry -> writeGroup(entry.getKey(), entry.getValue(), listFolders, parentPath));
    }



    public void writeGroup(int index, List<Integer> indexes,List<Path> paths, Path pathWhereToWrite) {
        File destdir = new File(pathWhereToWrite.toFile(), "common_com_batch_" + (index+1+startIndex));
        if(!destdir.exists()){
            destdir.mkdirs();
        }



        indexes.stream().forEach(k -> {
            try {
                FileUtils.copyDirectoryToDirectory(paths.get(k).toFile(), destdir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


}
