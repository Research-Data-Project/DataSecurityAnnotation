package org.lsi.research.datasecurity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class DataSecurityApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(DataSecurityApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DataSecurityApplication.class, args);



    }


}
