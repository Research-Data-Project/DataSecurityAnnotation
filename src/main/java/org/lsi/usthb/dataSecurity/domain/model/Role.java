package org.lsi.usthb.dataSecurity.domain.model;

import lombok.Data;
import org.lsi.usthb.dataSecurity.domain.annotation.DataSecurityLevel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role {
    @Id
    @DataSecurityLevel(value ="HighlySensitive")
    private String name;
}
