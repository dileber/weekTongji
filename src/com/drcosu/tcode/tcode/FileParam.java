package com.drcosu.tcode.tcode;

import com.drcosu.tcode.app.Config;
import com.drcosu.tcode.main.Main;
import com.drcosu.tcode.utils.AttachmentStore;
import com.drcosu.tcode.utils.UTime;

import java.io.File;
import java.util.*;

public class FileParam {

    public static final String  ParentClass = "ParentClass";
    public static final String  NormalClass = "NormalClass";
    public static final String  NewFunction = "NewFunction";
    // 递归遍历
    public static void getDirectory(File file) {
        File flist[] = file.listFiles();
        if (flist == null || flist.length == 0) {
            return;
        }
        for (File f : flist) {
            if (f.isDirectory()) {
                //这里将列出所有的文件夹
                //System.out.println("Dir==>" + f.getAbsolutePath());
                getDirectory(f);
            } else {
                //这里将列出所有的文件
                //System.out.println("file==>" + f.getAbsolutePath());
                String s = AttachmentStore.loadAsString(f.getPath());

                read(s,"@ParentClass",ParentClass);
                read(s,"@NormalClass",NormalClass);
                read(s,"@NewFunction",NewFunction);


//                int parentClass = s.indexOf("@ParentClass");
//                int normalClass = s.indexOf("@NormalClass");
//                int newFunction = s.indexOf("@NewFunction");
//
//                if(parentClass!=-1){
//                    Main.str.get(ParentClass).add(s.substring(parentClass+14,parentClass+23));
//                    //System.out.println(s.substring(parentClass+14,parentClass+23));
//                }
//                if(normalClass!=-1){
//                    Main.str.get(NormalClass).add(s.substring(normalClass+14,normalClass+23));
//                    //System.out.println(s.substring(normalClass+14,normalClass+23));
//                }
//                if(newFunction!=-1){
//                    Main.str.get(NewFunction).add(s.substring(newFunction+14,newFunction+23));
//                    //System.out.println(s.substring(newFunction+14,newFunction+23));
//                }
            }
        }
    }

    public static void read(String s,String param,String name){
        int cls = s.indexOf(param);
        if(cls!=-1){
            for(int i=-1; i<=s.lastIndexOf(param);++i)
            {
                i=s.indexOf(param,i);
                if(i!=-1){
                    Main.str.get(name).add(s.substring(i+14,i+23));
                }
            }
        }

    }



    public static void tongji(Map<String, List<String>> str, long start, long end) throws Exception {
        int p = 0,n=0,f=0;
        for(Map.Entry<String, List<String>> m : str.entrySet()){
            for(String s:m.getValue()){
                Date d = UTime.getDateFromStr(UTime.Pattern.ylmld,s);

                if(d.getTime()>=start&&d.getTime()<=end){
                    switch (m.getKey()){
                        case ParentClass:
                            p++;
                            break;
                        case NormalClass:
                            n++;
                            break;
                        case NewFunction:
                            f++;
                            break;
                    }
                }
            }
        }
        StringBuffer s = new StringBuffer("基类封装："+p+"个\r\n");
        s.append("创建类："+n+"个\r\n");
        s.append("创建函数："+f+"个\r\n");
        AttachmentStore.add(s.toString(), Config.outdir+Config.outFile);
        System.out.println(s.toString());

    }


}
