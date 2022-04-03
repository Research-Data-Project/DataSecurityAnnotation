package org.lsi.usthb.dataSecurity.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class DataSecurityLevel {
    @Id
    private String level;

}
