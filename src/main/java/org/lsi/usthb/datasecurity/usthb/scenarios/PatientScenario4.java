package org.lsi.usthb.datasecurity.usthb.scenarios;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lsi.usthb.datasecurity.usthb.annotation.DataSecurityLevel;
import org.lsi.usthb.datasecurity.usthb.domain.model.FrequencyType;
import org.lsi.usthb.datasecurity.usthb.domain.model.SecurityLevel;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
public class PatientScenario4 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String firstName;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String lastName;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String email;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    private String password;
    @DataSecurityLevel(level = SecurityLevel.SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    private String profession;
    @DataSecurityLevel(level = SecurityLevel.SENSITIVE,frequency = FrequencyType.NONE)
    private String sexe;
    private Date birthDate;
    @DataSecurityLevel(level = SecurityLevel.SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    @Column(length = 65535, columnDefinition = "text")
    private String description;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    @Column(length = 65535, columnDefinition = "text")
    private String medicalHistory;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    @Column(length = 65535, columnDefinition = "text")
    private String medicalResults ;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    @Lob
    private byte[] medicalReport;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.FREQUENTILY_UPDATED)
    @Lob
    private byte[] scans;

    private int age;
    private int weigth;





}
