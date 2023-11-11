package org.fst.patientmvc.security.service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.fst.patientmvc.security.entities.AppRole;
import org.fst.patientmvc.security.entities.AppUser;
import org.fst.patientmvc.security.repositories.AppRoleRepository;
import org.fst.patientmvc.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService{

    private AppUserRepository appUserRepository ;
    private AppRoleRepository appRoleRepository ;

    private PasswordEncoder passwordEncoder ;
    @Transactional
    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)) throw  new RuntimeException("Password not match");
        String hashedPWD = passwordEncoder.encode(password) ;
        AppUser appUser = new AppUser() ;
        //gerener un id aleatoire
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPWD);
        appUser.setActive(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }
    @Transactional
    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole!=null)
        {
            throw new RuntimeException("Role "+roleName+"already exist") ;
        }
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole appRole1 = appRoleRepository.save(appRole) ;
         return appRole1;
    }
    @Transactional
    @Override
    public void addRoleToUser(String username, String roleName) {

        AppUser appUser = appUserRepository.findByUsername(username) ;
        if(appUser==null)
        {
            throw new RuntimeException("User not found") ;
        }
        AppRole appRole = appRoleRepository.findByRoleName(roleName) ;
        if(appRole==null)
        {
            throw new RuntimeException("Role not found") ;
        }
        appUser.getAppRoles().add(appRole) ;

    }
    @Transactional
    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username) ;
        if(appUser==null)
        {
            throw new RuntimeException("User not found") ;
        }
        AppRole appRole = appRoleRepository.findByRoleName(roleName) ;
        if(appRole==null)
        {
            throw new RuntimeException("Role not found") ;
        }
        appUser.getAppRoles().remove(appRole) ;
    }
    @Transactional
    @Override
    public AppUser loadUserByUserName(String username) {

        return appUserRepository.findByUsername(username);
    }
}
