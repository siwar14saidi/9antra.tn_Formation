package tn.antraFormationSpringBoot.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.repository.CoursRepository;

@Service
public class CoursService implements ICoursService{

	@Autowired
	CoursRepository coursRepository;

	@Override
	public Cours addCours(Cours cours) {
		// TODO Auto-generated method stub
		return coursRepository.save(cours);
	}

	@Override
	public List<Cours> getCours() {
		// TODO Auto-generated method stub
		return coursRepository.findAll();
	}

	@Override
	public void deleteCours(Long id) {
		coursRepository.deleteById(id);
		
	}

	@Override
	public Cours updateCours(Long id, Cours newCours) {
		// TODO Auto-generated method stub
		Cours oldCours = coursRepository.findById(id).get();
		
		if (newCours.getUrlImage() != null && !newCours.getUrlImage().isEmpty()) {
	        oldCours.setUrlImage(newCours.getUrlImage());
	    }

	    if (newCours.getNomCours() != null && !newCours.getNomCours().isEmpty()) {
	        oldCours.setNomCours(newCours.getNomCours());
	    }

	    if (newCours.getPrix() != null && newCours.getPrix() != 0) {
	        oldCours.setPrix(newCours.getPrix());
	    }

	    if (newCours.getDateDebut() != null) {
	        oldCours.setDateDebut(newCours.getDateDebut());
	    }
	    if (newCours.getDateFin() != null) {
	        oldCours.setDateFin(newCours.getDateFin());
	    }

	    if (newCours.getLib_cours() != null && !newCours.getLib_cours().isEmpty()) {
	        oldCours.setLib_cours(newCours.getLib_cours());
	    }
	    
	    if (newCours.getUrlVideo() != null && !newCours.getUrlVideo().isEmpty()) {
	        oldCours.setUrlVideo(newCours.getUrlVideo());
	    }

        return coursRepository.save(oldCours);
        
	}

	@Override
	public Boolean coursExist(Long id) {
		// TODO Auto-generated method stub
		        return coursRepository.existsById(id);
		    
	}

	@Override
	public Optional<Cours> getCoursById(Long id) {
		// TODO Auto-generated method stub
		return coursRepository.findById(id);
	}




	



}
