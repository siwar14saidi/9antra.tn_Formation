package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Optional;

import tn.antraFormationSpringBoot.entites.Manager;

public interface IManagerService {
	public List<Manager> getManager();
	public Optional<Manager> getManagerById(Long id);
	 public Manager createManager(Manager manager);
	 public String getMotDePasseNonCrypte(Long id);
	 public Manager editManager(Long id, Manager newManager);
}
