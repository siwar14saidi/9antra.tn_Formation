package tn.antraFormationSpringBoot.services;

import java.util.List;
import java.util.Optional;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;

public interface IFormateurService {
	public List<Formateur> getFormateur();
	public Optional<Formateur> getFormateurById(Long id);
	 public Formateur createFormateur(Formateur formateur);
	 public String getMotDePasseNonCrypte(Long id);
	 public Formateur editFormateur(Long id, Formateur newFormateur);
	 public Formateur getFormById(Long id);
		public Formateur getFById(Long id) ;

}
