package tn.antraFormationSpringBoot.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;

public interface IApprenantService {

//	public Apprenant addApprenant(Apprenant apprenant);
	public List<Apprenant> getApprenant();
	 public Apprenant createApprenant(Apprenant apprenant);
	 public String getMotDePasseNonCrypte(Long id);
	 public Apprenant editApprenant(Long id, Apprenant newApprenant);
	 public Apprenant getApprenantById(List<Apprenant> apprenants, Long id);
	public Apprenant getApprenantByCin(List<Apprenant> apprenants, int cinUser);
	 public Apprenant getAppById(Long id);
	 public Long getAById(List<Apprenant> apprenants, Long id);
	public Apprenant editAppgrp(Long id, Apprenant newApprenant);

	 
}
