/*package tn.antraFormationSpringBoot.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.services.ApprenantService;
import tn.antraFormationSpringBoot.services.FormateurService;
import tn.antraFormationSpringBoot.services.ManagerService;
import tn.antraFormationSpringBoot.services.AdminService;

@Service
public class userDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        Apprenant apprenant = apprenantService.findByEmail(email);
        if (apprenant != null) {
            userDetails = org.springframework.security.core.userdetails.User.withUsername(apprenant.getEmail())
                    .password(apprenant.getMotDePasse())
                    .authorities("apprenant")
                    .build();
        }

        if (userDetails == null) {
            Formateur formateur = formateurService.findByEmail(email);
            if (formateur != null) {
                userDetails = org.springframework.security.core.userdetails.User.withUsername(formateur.getEmail())
                        .password(formateur.getMotDePasse())
                        .authorities("formateur")
                        .build();
            }
        }

        if (userDetails == null) {
            Manager manager = managerService.findByEmail(email);
            if (manager != null) {
                userDetails = org.springframework.security.core.userdetails.User.withUsername(manager.getEmail())
                        .password(manager.getMotDePasse())
                        .authorities("manager")
                        .build();
            }
        }

        if (userDetails == null) {
            Admin admin = adminService.findByEmail(email);
            if (admin != null) {
                userDetails = org.springframework.security.core.userdetails.User.withUsername(admin.getEmail())
                        .password(admin.getMotDePasse())
                        .authorities("admin")
                        .build();
            }
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }

        return userDetails;
    }
} */
