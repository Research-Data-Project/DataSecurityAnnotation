package org.lsi.usthb.datasecurity.usthb.scenarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepositoryBis extends JpaRepository<PatientScenario3,Long> {
}
