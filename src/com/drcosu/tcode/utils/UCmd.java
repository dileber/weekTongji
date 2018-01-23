package com.drcosu.tcode.utils;

import java.io.*;

public class UCmd {

    public interface M{
        void myFinally();
    }

    public synchronized static void startCmd(String path,M m){
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            Process ps = Runtime.getRuntime().exec(path);
            InputStream in = ps.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);

            BufferedReader bufferedReader = new BufferedReader(isr);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }

            in.close();
            isr.close();
            bufferedReader.close();
            ps.waitFor();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(m!=null){
                m.myFinally();
            }
        }

    }

    /**执行外部程序,并获取标准输出*/
    public static String excuteCmd_multiThread(String cmd,String encoding,M m)
    {
        BufferedReader bReader=null;
        InputStreamReader sReader=null;
        try
        {
            Process p = Runtime.getRuntime().exec(cmd);

            /*为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/
            Thread t=new Thread(new InputStreamRunnable(p.getErrorStream(),"ErrorStream"));
            t.start();

            /*"标准输出流"就在当前方法中读取*/
            BufferedInputStream bis = new BufferedInputStream(p.getInputStream());

            if(encoding!=null && encoding.length()!=0)
            {
                sReader = new InputStreamReader(bis,encoding);//设置编码方式
            }
            else
            {
                sReader = new InputStreamReader(bis,"GBK");
            }
            bReader=new BufferedReader(sReader);

            StringBuilder sb=new StringBuilder();
            String line;

            while((line=bReader.readLine())!=null)
            {
                sb.append(line);
                sb.append("/n");
            }

            bReader.close();
            p.destroy();
            return sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "ErrorString";
        }
        finally
        {
            if(m!=null){
                m.myFinally();
            }
        }
    }

    /**读取InputStream的线程*/
    static class InputStreamRunnable implements Runnable
    {
        BufferedReader bReader=null;
        String type=null;
        public InputStreamRunnable(InputStream is, String _type)
        {
            try
            {
                bReader=new BufferedReader(new InputStreamReader(new BufferedInputStream(is),"UTF-8"));
                type=_type;
            }
            catch(Exception ex)
            {
            }
        }
        public void run()
        {
            String line;
            int lineNum=0;

            try
            {
                while((line=bReader.readLine())!=null)
                {
                    lineNum++;
                    //Thread.sleep(200);
                }
                bReader.close();
            }
            catch(Exception ex)
            {
            }
        }
    }
}
