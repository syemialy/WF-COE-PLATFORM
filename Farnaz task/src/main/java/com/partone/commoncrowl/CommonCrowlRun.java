package com.partone.commoncrowl;

import com.partone.HelpRunner;

import java.io.File;
import java.util.Arrays;

public class CommonCrowlRun {

    private static String folderPath = "C:\\temp\\extracted_common_com\\output_5";

    public static void main(String[] args) {
        File file = new File(folderPath);
        handleBloknot(file);
    }

    private static void handleBloknot(File file) {
        System.out.print("Handle bloknot: " + file.toString());
        HelpRunner helpRunner = new HelpRunner();
        try {
            helpRunner.run(file.getPath(), file.getParentFile().getName(), file.getName());
        } catch (Exception e) {
            System.out.print("Exception in Handle bloknot: " + file.toString());
            e.printStackTrace();
        }
    }
}
