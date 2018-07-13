package com.parttwo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectImages {

    int countThreads = 5;

    public static void main(String[] args) throws InterruptedException {
        CollectImages collectImages = new CollectImages();
        collectImages.doWork();
    }

    private void doWork() throws InterruptedException {
        List<String> apps = new FileUtil().readFile("C:\\important\\workfusion_task\\Aleh_Hlushkou\\list_apps.txt");
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);
        Set<Callable<Void>> callables = initRunnables(apps);
        executorService.invokeAll(callables);
        executorService.shutdown();
    }

    private Set<Callable<Void>> initRunnables(List<String> apps) {
        Set<Callable<Void>> result = new HashSet<>();
        apps.forEach(
                app -> {
                    Callable r = () -> {this.collect(app);return null;};
                    result.add(r);
                }
        );
        return result;
    }

    private void collect(String app) {
        ExtractInfWebPage extractInfWebPage = new ExtractInfWebPage();
        List<String> urls = extractInfWebPage.extractUrls(app);
        SaveImages saveImages = new SaveImages();
        saveImages.save(urls, app);
    }
}
