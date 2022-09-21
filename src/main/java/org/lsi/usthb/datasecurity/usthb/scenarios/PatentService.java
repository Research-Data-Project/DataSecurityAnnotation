package org.lsi.usthb.datasecurity.usthb.scenarios;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.util.Date;

public class PatentService {


    @Autowired
    private  PatientRepository patientRepository;

    public void scneario1() throws IOException {
        Writer w = new FileWriter(new File("c:/test/scenario1.txt"));
        MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();
        OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(
                mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        Date startTime = new Date();
        long memoryUsageAtStart = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long nanoBefore = System.nanoTime();
        long cpuBefore = osMBean.getProcessCpuTime();

        /*** process data */
        Patient p = getPatientInstance();
        patientRepository.save(p);
        long cpuAfter = osMBean.getProcessCpuTime();
        long nanoAfter = System.nanoTime();
        long percent;
        if (nanoAfter > nanoBefore)
            percent = ((cpuAfter - cpuBefore) * 100L) /
                    (nanoAfter - nanoBefore);
        else percent = 0;
        double load = osMBean.getSystemLoadAverage();
        long memoryUsageAtEnd = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Date endTime = new Date();
        w.write("Time to process data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("Memory usage (bit) : "+(memoryUsageAtEnd-memoryUsageAtStart));
        w.write("\n");
        w.write("CPU usage (%): "+(load));
        w.flush();
        w.close();
    }


    public Patient getPatientInstance(){
        Patient p = new Patient();
        p.setFirstName("firstname");
        p.setLastName("lastname");
        return p;
    }


}
