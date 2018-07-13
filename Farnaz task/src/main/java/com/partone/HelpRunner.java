package com.partone;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelpRunner {

    private final String driverPath = "C:\\RPAExpress\\RPA\\rpa-grid\\drivers\\windows\\x32\\chromedriver.exe";


    public void run(String pathToFile, String nameFolder, String nameFile ) throws FileNotFoundException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", driverPath);
        final List<String> list = new FileUitl().fileToList(pathToFile);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ScreenshootService screenshootService = new ScreenshootService();
        Set<Callable<String>> callables = screenshootService.initCallables(list, nameFolder, nameFile);
        executorService.invokeAll(callables);
        executorService.shutdown();
    }


}
