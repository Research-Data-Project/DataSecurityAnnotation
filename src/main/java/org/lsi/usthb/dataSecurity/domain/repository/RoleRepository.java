package org.lsi.usthb.dataSecurity.domain.repository;

import org.lsi.usthb.dataSecurity.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
