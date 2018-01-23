package com.drcosu.tcode.tcode;

import com.drcosu.tcode.utils.AttachmentStore;
import com.drcosu.tcode.utils.UCmd;
import com.drcosu.tcode.app.*;

import java.io.*;

public class CodeLine {



    public static void create(String start,String end,String dir,String projectSrc,String bat,String log,String out) {
//        svn log -v --xml -r {2018-01-15}:{2018-01-20} > svn.log
//        java -jar startSvn.jar svn.log ./ -output-dir ./report

        String title = "@echo off\r\n"+dir+"\r\ncd " + projectSrc + "\r\n";

        String commend = title+ "svn log -v --xml -r {" + start + "}:{" + end + "} > " + log +"\r\nexit";
        AttachmentStore.save(bat, commend);
        UCmd.startCmd( bat, new UCmd.M() {
            @Override
            public void myFinally() {
//                String commend =  "@echo off\r\njava -jar "+projectSrc+"startSvn.jar " + projectSrc+log + " "+projectSrc+" -output-dir " + projectSrc+out +"\r\nexit";
//                AttachmentStore.save(projectSrc + bat, commend);
                String commend =  "java -jar config/startSvn.jar " +log + " "+projectSrc+" -output-dir " +out ;

                UCmd.excuteCmd_multiThread(commend, null, new UCmd.M() {
                    @Override
                    public void myFinally() {
                        InputStreamReader isr = null;
                        try {
                            isr = new InputStreamReader(new FileInputStream(out + "\\index.html"), "UTF-8");
                            BufferedReader br = new BufferedReader(isr);
                            String str = null;
                            while((str = br.readLine()) != null) {
                                int index = str.indexOf("Total Lines of Code:");
                                if(index!=-1){
                                    String s = br.readLine();
                                    s = s.replace("    <dd>","总代码行数：");
                                    s = s.replace("</dd>","\r\n");
                                    AttachmentStore.add(s,Config.outdir+Config.outFile);
                                    System.out.println(s);
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


//                try {
//                    Runtime.getRuntime().exec(commend);
//                    InputStreamReader isr = null;
//                    try {
//                        isr = new InputStreamReader(new FileInputStream(projectSrc + out + "\\index.html"), "UTF-8");
//                        BufferedReader br = new BufferedReader(isr);
//                        String str = null;
//                        while((str = br.readLine()) != null) {
//                            int index = str.indexOf("Total Lines of Code:");
//                            if(index!=-1){
//                                String s = br.readLine();
//                                s = s.replace("    <dd>","总代码行数：");
//                                s = s.replace("</dd>","\r\n");
//                                AttachmentStore.add(s,Config.outFile);
//                                System.out.println(s);
//                            }
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


//                UCmd.startCmd(commend, new UCmd.M() {
//                    @Override
//                    public void myFinally() {
//
//
//                        InputStreamReader isr = null;
//                        try {
//                            isr = new InputStreamReader(new FileInputStream(projectSrc + out + "\\index.html"), "UTF-8");
//                            BufferedReader br = new BufferedReader(isr);
//                            String str = null;
//                            while((str = br.readLine()) != null) {
//                                int index = str.indexOf("Total Lines of Code:");
//                                if(index!=-1){
//                                    String s = br.readLine();
//                                    s = s.replace("    <dd>","总代码行数：");
//                                    s = s.replace("</dd>","\r\n");
//                                    AttachmentStore.add(s,Config.outFile);
//                                    System.out.println(s);
//                                }
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });

            }
        });









    }
}
