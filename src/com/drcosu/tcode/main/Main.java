package com.drcosu.tcode.main;

import com.drcosu.tcode.app.*;
import com.drcosu.tcode.tcode.CodeLine;
import com.drcosu.tcode.tcode.FileParam;
import com.drcosu.tcode.utils.AttachmentStore;
import com.drcosu.tcode.utils.UTime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Map<String,List<String>> str = new HashMap<>();


    public static void main(String[] args) {

        JFrame frame = new JFrame("统计一周代码");
        Week week = new Week();

        frame.setContentPane(week.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        week.setOnclick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = UTime.getFirstDayOfWeek(UTime.Pattern.y_m_d);
                String end = UTime.getLastDayOfWeek(UTime.Pattern.y_m_d);
                System.out.println("统计本周代码"+start+"到"+end);
                Config.init();
                AttachmentStore.delete(Config.outdir+Config.outFile);
                AttachmentStore.add("统计本周代码"+start+"到"+end+"\r\n",Config.outdir+Config.outFile);
                CodeLine.create(start,end,Config.dir,Config.src,
                        Config.outdir+Config.bat,
                        Config.outdir+Config.log,
                        Config.outdir+Config.out);
                String[] sm = Config.coldCheck.split(",");
                str.put(FileParam.ParentClass,new ArrayList<>());
                str.put(FileParam.NormalClass,new ArrayList<>());
                str.put(FileParam.NewFunction,new ArrayList<>());
                for(String s:sm){
                    FileParam.getDirectory(new File(s));
                }
                try {
                    FileParam.tongji(str,UTime.getDateFromStr(UTime.Pattern.y_m_d,start).getTime(),UTime.getDateFromStr(UTime.Pattern.y_m_d,end).getTime());
                } catch (Exception n) {
                    n.printStackTrace();
                }
                frame.dispose();
            }
        });



    }
}
