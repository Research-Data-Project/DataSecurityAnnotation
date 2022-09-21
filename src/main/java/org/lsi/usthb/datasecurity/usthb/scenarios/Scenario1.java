package org.lsi.usthb.datasecurity.usthb.scenarios;

import com.sun.management.OperatingSystemMXBean;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.AES;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.AESWitoutKeyEncryption;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.ECC;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.ECC2;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.model.CryptoOperationType;
import org.lsi.usthb.datasecurity.usthb.infra.Kernel32;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.management.MBeanServerConnection;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/***
 *
 * Read and write data without encryption
 *
 */
@RestController
@RequestMapping("/scenario1")
public class Scenario1 extends AbstractScenario{

    AESWitoutKeyEncryption aes = new AESWitoutKeyEncryption();
    ECC2 ecc = new ECC2();

    @Autowired
    PatientRepository patientRepository;

    String opType = System.getenv("type");

    @GetMapping("/write")
    public void write() throws IOException {
        writeDataScenario1();
    }

    @GetMapping("/read")
    public void read() throws IOException {
        readDataScenario1();
    }



    private  void writeDataScenario1() throws IOException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double cpuUsage=0d;
        Writer w = new FileWriter(new File("c:/test/writeScenario1.txt"));
        Date startTime = new Date();


        List<Patient> patientList = DataPopulation.populateData();
        for(Patient p : patientList){
            /*** first encrypt with AES */
            p.setLastName(aes.encryptData(p.getLastName(), 192));
            p.setFirstName(aes.encryptData(p.getFirstName(), 192));
            p.setEmail(aes.encryptData(p.getEmail(), 192));
            p.setSexe(aes.encryptData(p.getSexe(), 192));
            p.setBirthDate(new Date());
            p.setAge(35);
            p.setWeigth(172);
            p.setDescription(aes.encryptData(p.getDescription(), 192));
            p.setPassword(aes.encryptData(p.getPassword(), 192));
            p.setProfession(aes.encryptData(p.getProfession(), 192));
            p.setMedicalResults(aes.encryptData(p.getMedicalResults(), 192));
            p.setMedicalHistory(aes.encryptData(p.getMedicalHistory(), 192));
            p.setScans(aes.encryptData(p.getScans(), 192));
            p.setMedicalReport(aes.encryptData(p.getMedicalReport(), 192));

            /*** first encrypt with ECC */
            p.setLastName(ecc.encryptData(p.getLastName(), 192));
            p.setFirstName(ecc.encryptData(p.getFirstName(), 192));
            p.setEmail(ecc.encryptData(p.getEmail(), 192));
            p.setSexe(ecc.encryptData(p.getSexe(), 192));
            p.setBirthDate(new Date());
            p.setAge(35);
            p.setWeigth(172);
            p.setDescription(ecc.encryptData(p.getDescription(), 192));
            p.setPassword(ecc.encryptData(p.getPassword(), 192));
            p.setProfession(ecc.encryptData(p.getProfession(), 192));
            p.setMedicalResults(ecc.encryptData(p.getMedicalResults(), 192));
            p.setMedicalHistory(ecc.encryptData(p.getMedicalHistory(), 192));
            p.setScans(ecc.encryptData(p.getScans(), 192));
            p.setMedicalReport(ecc.encryptData(p.getMedicalReport(), 192));
            /** save data */

        };
        Date endTime = new Date();
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        w.write("Time to encrypt data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage for cryptung (%): "+(cpuUsage));
        startTime = new Date();
        cpuUsage=0d;
        saveData( patientList);
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        endTime = new Date();
        w.write("\n");
        w.write("Time to save data (ms) : "+(endTime.getTime()-startTime.getTime()));
        w.write("\n");
        w.write("CPU usage for saving (%): "+(cpuUsage));
        w.flush();
        w.close();
    }


    private  void readDataScenario1() throws IOException {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Writer w = new FileWriter(new File("c:/test/readScenario1.txt"));
        Date startTime = new Date();
        double cpuUsage=0d;
        long memoryUsageAtStart = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();


        /*** process data */
        List<Patient> patients= getData();
        Date endTime = new Date();

        w.write("Time to retreive  data for database (ms) : "+(endTime.getTime()-startTime.getTime()));
        startTime = new Date();
        cpuUsage=cpuUsage+operatingSystemMXBean.getProcessCpuLoad();
        w.write("\n");
        w.write("CPU usage for getting data for database (%): "+(cpuUsage));
        List<Patient> ps = new ArrayList<>();
        cpuUsage=0d;
        for(Patient p : patients){
            /*** first decrypt with ECC */
            p.setLastName(ecc.decryptData(p.getLastName(), 192));
            p.setFirstName(ecc.decryptData(p.getFirstName(), 192));
            p.setEmail(ecc.decryptData(p.getEmail(), 192));
            p.setSexe(ecc.decryptData(p.getSexe(), 192));
            p.setBirthDate(new Date());
            p.setAge(35);
            p.setWeigth(172);
            p.setDescription(ecc.decryptData(p.getDescription(), 192));
            p.setPassword(ecc.decryptData(p.getPassword(), 192));
            p.setProfession(ecc.decryptData(p.getProfession(), 192));
            p.setMedicalResults(ecc.decryptData(p.getMedicalResults(), 192));
            p.setMedicalHistory(ecc.decryptData(p.getMedicalHistory(), 192));
            p.setScans(ecc.decryptData(p.getScans(), 192));
            p.setMedicalReport(ecc.decryptData(p.getMedicalReport(), 192));
            /* decrypt with AES **/
            p.setLastName(aes.decryptData(p.getLastName(), 192));
            p.setFirstName(aes.decryptData(p.getFirstName(), 192));
            p.setEmail(aes.decryptData(p.getEmail(), 192));
            p.setSexe(aes.decryptData(p.getSexe(), 192));
            p.setBirthDate(new Date());
            p.setAge(35);
            p.setWeigth(172);
            p.setDescription(aes.decryptData(p.getDescription(), 192));
            p.setPassword(aes.decryptData(p.getPassword(), 192));
            p.setProfession(aes.decryptData(p.getProfession(), 192));
            p.setMedicalResults(aes.decryptData(p.getMedicalResults(), 192));
            p.setMedicalHistory(aes.decryptData(p.getMedicalHistory(), 192));
            p.setScans(aes.decryptData(p.getScans(), 192));
            p.setMedicalReport(aes.decryptData(p.getMedicalReport(), 192));
            /** data */
            ps.add(p);
        };

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
