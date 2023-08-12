package tn.antraFormationSpringBoot.services;

import java.util.List;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Groupe;

public interface IGroupeService {
	public List<Groupe> getGroupe();
	public Groupe getGroupeById(Long id) ;
	public Groupe addGroupe(Groupe groupe);
	 public Groupe updateGroupe(Long groupeId, Groupe newGroupe);
	  public List<Apprenant> getApprenantsByGroupeId(Long groupeId);
}
