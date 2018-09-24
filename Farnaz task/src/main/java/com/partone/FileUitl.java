package com.partone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUitl {

    public List<String> fileToList(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        List<String> list = new ArrayList<String>();
        s.next();
        while (s.hasNext()){
            list.add(s.next().replaceAll("\"", ""));
        }
        s.close();
        return list;
    }

    public static void main(String ...args) throws FileNotFoundException {
        List<String> list = new FileUitl().fileToList("C:\\temp\\extracted_common_com\\collected.txt");
        System.out.println(list.get(1));
    }

}
