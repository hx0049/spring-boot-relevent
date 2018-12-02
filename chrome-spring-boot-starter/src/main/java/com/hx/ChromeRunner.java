package com.hx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChromeRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String url;
    private List<String> chromePaths = new ArrayList<String>();

    void init(ChromeProperty chromeProperty){
        if(null != chromeProperty.getPath()){
            chromePaths.add(chromeProperty.getPath());
        }
        chromePaths.add("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        chromePaths.add("C:\\User\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        url = chromeProperty.getUrl();
    }

    public void run(String... args) throws Exception {
        if(!isRunningInWindows()){
            return;
        }
        if(isChromeAlreadyInProcess()){
            return;
        }
        initUrl(args);
        startChrome();
    }

    private void startChrome() {
        if (url == null) {
            logger.warn("Url is not specified, chrome will not be started!");
            logger.warn("Please specified url by chrome.url(recommend) or Program argments[0]");
            return;
        }
        for (String chromePath : chromePaths) {
            if(!new File(chromePath).exists()){
                continue;
            }
            try{
                Runtime.getRuntime().exec(chromePath + " " + url+" -incognito --start-maximized");
            }catch (IOException e){
                logger.error("Error when starting chrome.exe.");
            }
        }
    }

    private void initUrl(String... args) {
        if(url == null){
            if(args != null && args.length != 0){
                url = args[0];
            }
        }
        if(null != url && !url.startsWith("http")){
            url = "http://"+url;
        }
    }


    private boolean isChromeAlreadyInProcess() {
        InputStream is = null;
        try {
            is = Runtime.getRuntime().exec("tasklist /svc")
                    .getInputStream();
            String taskInfo = getStringFromInputStream(is);
            return taskInfo.contains("chrome.exe");
        }catch (Exception e){
            return false;
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info("Error happens when close cmd input stream: "+ e.getMessage());
                }
            }
        }
    }

    private boolean isRunningInWindows() {
        String os = System.getProperty("os.name");
        logger.info("Current System is "+os);
        return os.toLowerCase().startsWith("win");
    }

    private String getStringFromInputStream(InputStream is){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine())!= null){
                sb.append(line);
            }
        } catch (Exception e) {
           logger.error("Error happens when reading from cmd process: "+e.getMessage());
        }
        return sb.toString();
    }
}
