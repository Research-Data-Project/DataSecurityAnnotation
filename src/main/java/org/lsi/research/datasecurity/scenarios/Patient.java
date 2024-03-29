package org.lsi.research.datasecurity.scenarios;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String firstName;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String lastName;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String email;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String password;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String profession;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    private String sexe;
    private Date birthDate;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    @Column(length = 65535, columnDefinition = "text")
    private String description;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    @Column(length = 65535, columnDefinition = "text")
    private String medicalHistory;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    @Column(length = 65535, columnDefinition = "text")
    private String medicalResults ;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    @Lob
    private byte[] medicalReport;
    @DataSecurityLevel(level = SecurityLevel.HIGHLY_SENSITIVE,frequency = FrequencyType.NONE)
    @Lob
    private byte[] scans;

    private int age;
    private int weigth;





}
