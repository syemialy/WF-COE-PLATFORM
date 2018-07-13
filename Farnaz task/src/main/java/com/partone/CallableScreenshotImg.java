package com.partone;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class CallableScreenshotImg implements Callable<String>, Comparable<CallableScreenshotImg> {

    int index;
    String url;
    String nameOriginalFolder;
    String nameFile;
    String startFolderPath;
    Set<String> timeoutSet;

    final String delimeter = "//";

    CallableScreenshotImg(int index, String url, String folderName, String nameFile, String startFolderName, Set<String> timeoutSet) {
        this.index = index;
        this.url = url;
        this.nameOriginalFolder = folderName;
        this.nameFile = nameFile;
        this.startFolderPath = startFolderName;
        this.timeoutSet = timeoutSet;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String call() {
        String url = this.url;
        System.out.println(index);
        if (checkInTimeoutSet(url, timeoutSet)) return "";
        //TODO run to commoncrowl
        if (checkExistHost(startFolderPath, nameOriginalFolder, url)) return "";
        if (!checkUrl(url, timeoutSet)) return "";
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-browser-side-navigation", "--incognito");
        options.addArguments("--disable-extensions");
        options.addArguments("test-type");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("no-sandbox");
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1440, 900));
        try {
            driver.navigate().to(url);
            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            String host = createFolders(startFolderPath, nameOriginalFolder, url);
            StringBuilder sb = new StringBuilder();
            sb.append(startFolderPath).append(delimeter).append(nameOriginalFolder)
                    .append(delimeter).append(host).append(delimeter)
                    .append(host).append("_").append(index).append(".png");
            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(sb.toString()));
            recordLast(index);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return java.lang.String.valueOf(index);
        } finally {
            driver.quit();
            driver.close();
        }
    }

    private boolean checkInTimeoutSet(String _url, Set<String> timeoutSet) {
        URL url = null;
        try {
            url = new URL(_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return true;
        }
        String host = url.getHost();
        if (timeoutSet.contains(host)) return true;
        else return false;
    }

    public boolean checkExistHost(String originalPath, String originalFolder, String _url) {
        URL url = null;
        try {
            url = new URL(_url);
        } catch (MalformedURLException e) {
            return true;
        }
        String host = url.getHost();
        try {
            File file = new File(originalPath + "//" + originalFolder + "//" + host);
            return file.exists();
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

    private void recordLast(int index) throws IOException {
        String nameFile = "last.txt";
        StringBuilder sb = new StringBuilder().append(startFolderPath).append(delimeter).append(nameFile);
        String item = String.valueOf(index) + System.lineSeparator();
        Files.write(Paths.get(sb.toString()), item.getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * @return name of the folder equal to a domen
     */
    private String createFolders(String originalPath, String originalFolder, String urlPath) throws MalformedURLException {
        new File(originalPath + "//" + originalFolder).mkdirs();
        String host = new URL(urlPath).getHost();
        new File(originalPath + delimeter + originalFolder + delimeter + host).mkdirs();
        return host;
    }

    public boolean checkUrl(String _url, Set<String> timeoutSet) {
        URL url = null;
        try {
            url = new URL(_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        String host = url.getHost();
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            if (code == 200 || code / 100 == 3) return true;
            else return false;
        } catch (Exception e) {
            timeoutSet.add(host);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int compareTo(CallableScreenshotImg o) {
        if (index > o.getIndex()) return 1;
        else return -1;
    }
}
