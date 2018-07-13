package com.partone.manheim;

import com.partone.HelpRunner;

import java.io.FileNotFoundException;

public class ManheimRun {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        HelpRunner helpRunner = new HelpRunner();
        helpRunner.run("C:\\important\\workfusion_task\\links.csv", "manheim", "manheim");
    }
}
