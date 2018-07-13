package com.parttwo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.stream.Collectors;

public class ExtractInfWebPage {

    public String query = "https://www.google.com/search?tbm=isch&hl=en&as_q=&safe=images&tbs=isz:lt,islt:vga,sur:fmc#imgrc=_";
    public String driverPath = "C:\\RPA\\rpa-grid\\drivers\\windows\\x32\\chromedriver.exe";

    public List<String> extractUrls(String _app){
        System.setProperty("webdriver.chrome.driver", driverPath);
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-browser-side-navigation");
        String queryWithApp = insertAppIntoQuery(_app);
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to(queryWithApp);
        scroolPage((JavascriptExecutor) driver);
        List<WebElement> webElementList = driver.findElements(By.xpath("//*[@id='rg_s']/div/div"));
        List<String> texts = webElementList.stream().map(element -> element.getAttribute("innerText")).collect(Collectors.toList());
        List<String> urls = texts.stream().map(ExtractService::extractUrl).collect(Collectors.toList());
        driver.close();
        return urls;
    }

    private void scroolPage(JavascriptExecutor driver) {
        JavascriptExecutor js = driver;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            js.executeScript("window.scrollBy(0,1000)", "");
        }
    }

    private String insertAppIntoQuery(String _app) {
        String app = _app.replaceAll(" ", "+");
        int index = query.indexOf("as_q=");
        StringBuilder sb = new StringBuilder(query);
        sb.insert(index + 5, app);
        return sb.toString();
    }
}
