package org.lsi.research.datasecurity.scenarios;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/***
 *
 * Read and write data with encryption
 *
 */
@RestController
@RequestMapping("/scenario2")
public class Scenario2 extends AbstractScenario{


    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/write")
    public void write() throws IOException, IllegalAccessException, NoSuchAlgorithmException {
        writeDataScenario2();

    }

    @GetMapping("/read")
    public void read() throws IOException {
        readDataScenario2();

    }



    private  void writeDataScenario2() throws IOException, IllegalAccessException, NoSuchAlgorithmException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
        long prevUpTime = runtimeMXBean.getUptime();
        long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
        double cpuUsage =0d;
        Writer w = new FileWriter(new File("c:/test/writeScenario2.txt"));

        Date startTime = new Date();
        long memoryUsageAtStart = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();


        /*** process data */
        List<Patient> patientList = DataPopulation.populateData();
        for(Patient patient : patientList){
            try {
                LevelSecurityUtility.applySecurityLevel(patient, CryptoOperationType.ENCRYPT);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        };
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        Date endTime = new Date();
        w.write("\n");
        w.write("Time to crypt data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage (%): "+(cpuUsage));
        startTime=new Date();
        cpuUsage=0d;
        saveData( patientList);
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        endTime = new Date();
        /** END **/
        w.write("\n");
        w.write("Time to save data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage (%): "+(cpuUsage));


        w.flush();
        w.close();
    }


    private  void readDataScenario2() throws IOException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuUsage=0d;
        Writer w = new FileWriter(new File("c:/test/readScenario2.txt"));

        Date startTime = new Date();


        /*** process data */
        List<Patient> ps =  getData();
        Date endTime = new Date();
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        w.write("\n");
        w.write("Time to get data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage (%): "+(cpuUsage));
        cpuUsage=0d;
        startTime=new Date();
        for(Patient patient : ps){
            try {
                LevelSecurityUtility.applySecurityLevel(patient, CryptoOperationType.DECRYPT);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        };
        /** END **/
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        endTime = new Date();
        w.write("\n");
        w.write("Time to decrypt data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage (%): "+(cpuUsage));
        w.flush();
        w.close();
    }

    private void saveData(List<Patient> patientList){
        patientRepository.saveAll(patientList);
    }

    private List<Patient> getData(){
        return patientRepository.findAll();
    }
}
