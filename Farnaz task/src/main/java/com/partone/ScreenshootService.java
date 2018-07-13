package com.partone;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenshootService {

    int init = 0;
    String startFolderName = "C://temp//task_wf_webpages";

    public Set<Callable<String>> initCallables(final List<String> list, String folderName, String nameFile) {
        Set<Callable<String>> callables = new TreeSet<>();
        Set<String> timeoutSet = new HashSet<>();
        for (int i = init; i < list.size() - 1; i++) {
            String url = list.get(i);
            CallableScreenshotImg callable = new CallableScreenshotImg(i, url, folderName, nameFile, startFolderName,timeoutSet);
            callables.add(callable);
        }
        return callables;
    }
}
        