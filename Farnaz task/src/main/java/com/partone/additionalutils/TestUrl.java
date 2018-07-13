package com.partone.additionalutils;

import java.net.MalformedURLException;
import java.net.URL;

public class TestUrl {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://4pda.ru/2018/05/04/351095/");
        System.out.println(url);
    }
}
