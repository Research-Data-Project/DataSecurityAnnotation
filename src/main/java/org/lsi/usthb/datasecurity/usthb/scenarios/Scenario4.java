package org.lsi.usthb.datasecurity.usthb.scenarios;

import com.sun.management.OperatingSystemMXBean;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.model.CryptoOperationType;
import org.lsi.usthb.datasecurity.usthb.domain.service.LevelSecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServerConnection;
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
@RequestMapping("/scenario4")
public class Scenario4 extends AbstractScenario{


    @Autowired
    PatientScenario4Repo patientRepository;

    @GetMapping("/write")
    public void write() throws IOException, IllegalAccessException, NoSuchAlgorithmException {
        writeDataScenario4();


    }

    @GetMapping("/read")
    public void read() throws IOException {
        readDataScenario4();

    }



    private  void writeDataScenario4() throws IOException, IllegalAccessException, NoSuchAlgorithmException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        double cpuUsage =0d;
        Writer w = new FileWriter(new File("c:/test/writeScenario4.txt"));

        Date startTime = new Date();


        /*** process data */
        List<PatientScenario4> patientList = DataPopulation.populateDataScenario4();
        for(PatientScenario4 patient : patientList){
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
        saveData(patientList);
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


    private  void readDataScenario4() throws IOException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuUsage=0d;
        Writer w = new FileWriter(new File("c:/test/readScenario4.txt"));

        Date startTime = new Date();


        /*** process data */
        List<PatientScenario4> ps =  getData();
        Date endTime = new Date();
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        w.write("\n");
        w.write("Time to get data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage (%): "+(cpuUsage));
        cpuUsage=0d;
        startTime=new Date();
        for(PatientScenario4 patient : ps){
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

    private void saveData(List<PatientScenario4> patientList){
        patientRepository.saveAll(patientList);
    }

    private List<PatientScenario4> getData(){
        return patientRepository.findAll();
    }
}
