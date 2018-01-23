package com.drcosu.tcode.app;

import com.drcosu.tcode.utils.UProperties;

import java.util.Map;

public class Config {

    public static String src;
    public static String dir;
    public static String bat;
    public static String out;
    public static String log;
    public static String outFile;
    public static String coldCheck;
    public static String outdir;


    public static void init(){
        Map<String,String> properties =  UProperties.readProperties();
        dir = properties.get("dir");
        src = properties.get("src");
        bat = properties.get("bat");
        out = properties.get("out");
        log = properties.get("log");
        outFile = properties.get("outFile");
        coldCheck = properties.get("coldCheck");
        outdir = properties.get("outdir");
    }

}
