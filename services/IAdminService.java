package tn.antraFormationSpringBoot.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tn.antraFormationSpringBoot.entites.Admin;
import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Manager;

public interface IAdminService {

	public void createAdmin(Admin admin);
	 public void deleteByEmail(String email);
	 public Admin getAdminByEmail(String email);
	 public Admin getAdminById(Long id);
		
}
