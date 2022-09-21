package org.lsi.usthb.datasecurity;


import com.sun.management.OperatingSystemMXBean;
import org.lsi.usthb.datasecurity.usthb.scenarios.Patient;
import org.lsi.usthb.datasecurity.usthb.scenarios.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.MBeanServerConnection;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class DataSecurityApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(DataSecurityApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DataSecurityApplication.class, args);



    }


}
