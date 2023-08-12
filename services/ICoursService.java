package tn.antraFormationSpringBoot.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;

public interface ICoursService {
	public List<Cours> getCours();
	public Cours addCours(Cours cours);
	public void deleteCours(Long id);
	public Cours updateCours(Long id, Cours cours);
	public Boolean coursExist(Long id);
	public Optional<Cours> getCoursById(Long id);
	
}
