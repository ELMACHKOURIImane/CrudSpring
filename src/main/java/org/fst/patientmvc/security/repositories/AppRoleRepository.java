package org.fst.patientmvc.security.repositories;

import org.fst.patientmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByRoleName(String roleName) ;
}
