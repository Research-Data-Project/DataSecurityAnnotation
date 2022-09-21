package org.lsi.usthb.datasecurity.usthb.scenarios;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class AbstractScenario {


    public void copyBatteryConsFile(String scneario) throws IOException {
        File dir = new File("C:\\Users\\NAZIME ATIMENE\\Desktop\\dev\\dataSecurity\\target");


        File[] files = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith("methods-filtered-power.csv");
            }
        });

        Files.copy(files[0].toPath(),new FileOutputStream(new File("c:/test/" + scneario + ".csv")));
    }
}
