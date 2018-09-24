package com.parttwo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractService {

    public static String extractUrl(String text) {
        Pattern p = Pattern.compile("ou\":\"(.*?)\"");
        Matcher m = p.matcher(text);
        String url = "";
        if (m.find()) {
            url = m.group(1);
        }
        return url;
    }
}
