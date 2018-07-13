package com.parttwo;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.List;

public class SaveImages {

    public void save(List<String> urls, String app) {
        int index = 0;
        for (String url_s : urls) {
            URL url = null;
            try {
                url = new URL(url_s);
                new File("C://temp//task_wf_app//"+app).mkdirs();
                ImageIO.write(ImageIO.read(url), "PNG", new File("C://temp//task_wf_app//"+app+"//" + index + ".png"));
                index++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
