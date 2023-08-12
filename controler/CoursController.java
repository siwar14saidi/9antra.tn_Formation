package tn.antraFormationSpringBoot.controler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.services.ApprenantService;
import tn.antraFormationSpringBoot.services.CoursService;
import tn.antraFormationSpringBoot.services.FileService;

@RestController
@RequestMapping("/Cours")
@CrossOrigin(origins = "http://localhost:4200")
public class CoursController {

	String subPath = "cours";
    String subPathFile = "cours/file";
    String subPathCreator = "cours/creator";
    
	@Autowired
	 CoursService coursService;
	
	@Autowired
	 FileService serviceFile;
	   
	//GetCours
	 @GetMapping("/all")
	    public List<Cours> getCours(){
	        return coursService.getCours();
	    }
	   
	 //addCours 
	@PostMapping("/add")
	public ResponseEntity addCours(
	        @RequestParam(name = "urlImage", required = false) MultipartFile urlImage,
	        @RequestParam(name = "nomCours", required = false) String nomCours,
	        @RequestParam(name = "prix", required = false) Double prix,
	        @RequestParam(name = "dateDebut", required = false) Date dateDebut,
	        @RequestParam(name = "dateFin", required = false) Date dateFin,
	        @RequestParam(name = "lib_cours", required = false) String lib_cours,
	        @RequestParam(name = "descriptionP", required = false) String descriptionP,
	        @RequestParam(name = "urlVideo", required = false) MultipartFile urlVideo) {

	    String imageCreatorName = "";
	    String videoCreatorName = "";
	    
	    if (urlImage != null) {
	        imageCreatorName = serviceFile.saveFile(urlImage, this.subPathCreator);
	    }

	    if (urlVideo != null) {
	        videoCreatorName = serviceFile.saveFile(urlVideo, this.subPathCreator);
	    }

	    Cours cours = new Cours();
	    cours.setUrlImage(imageCreatorName);
	    cours.setNomCours(nomCours);
	    cours.setPrix(prix);
	    cours.setDateDebut(dateDebut);
	    cours.setDateFin(dateFin);
	    String videoName = videoCreatorName != null ? videoCreatorName : ""; // Set the video name to empty string if it is null
	    cours.setUrlVideo(		videoName); // Append the video name to the videos folder path

	    cours.setLib_cours(lib_cours);

	    return ResponseEntity.ok(coursService.addCours(cours));
	}
	
	//DeletCours 
	 @DeleteMapping("/{id}/delete")
	 public ResponseEntity<Object> deleteCours(@PathVariable("id") Long id){
		 Optional<Cours> cours = coursService.getCoursById(id);
	        if (cours.isEmpty()) {
	            ResponseEntity.badRequest().build();
	        }
	        String imageCreatorName = cours.get().getUrlImage();
	        if (!imageCreatorName.equals("")) {

	            serviceFile.deleteFile(this.subPathCreator + "/" + imageCreatorName);
	        } 
	        String videoCreatorName = cours.get().getUrlVideo();
	        if (!videoCreatorName.equals("")) {

	            serviceFile.deleteFile(this.subPathCreator + "/" + videoCreatorName);
	        }
	        coursService.deleteCours(id);
	        return ResponseEntity.ok().build();
	    }
	 
	 
	 //UpdateCours 
	 @PutMapping("/{id}/edit")
	 public Cours updateCours(
			    @RequestParam(name = "urlImage", required = false) MultipartFile urlImage,
		        @RequestParam(name = "nomCours", required = false) String nomCours,
		        @RequestParam(name = "prix", required = false) Double prix,
		        @RequestParam(name = "dateDebut", required = false) Date dateDebut,
		        @RequestParam(name = "dateFin", required = false) Date dateFin,
		        @RequestParam(name = "lib_cours", required = false) String lib_cours,
		        @RequestParam(name = "descriptionP", required = false) String descriptionP,
		        @RequestParam(name = "urlVideo", required = false) MultipartFile urlVideo,
		        @PathVariable("id") Long id)
	 {
		 Optional<Cours> cours = coursService.getCoursById(id);
	        if (cours.isEmpty()) {
	            ResponseEntity.badRequest().build();
	        }       
	        
	       
	     
	        String imageCreatorName = cours.get().getUrlImage();
	        String videoCreatorName = cours.get().getUrlVideo();
	        
	        if (urlImage != null) {
	            if (!imageCreatorName.equals("")) {
	                serviceFile.deleteFile(this.subPathCreator + "/" + imageCreatorName);

	            }
	            imageCreatorName = serviceFile.saveFile(urlImage , this.subPathCreator);
	        }
	        if (urlVideo != null) {
	            if (!videoCreatorName.equals("")) {
	                serviceFile.deleteFile(this.subPathCreator + "/" + videoCreatorName);

	            }
	            videoCreatorName = serviceFile.saveFile(urlVideo , this.subPathCreator);
	        }
	        
	        Cours newCours = new Cours();
	        newCours.setUrlImage(imageCreatorName);
	        newCours.setNomCours(nomCours);
	        newCours.setPrix(prix);
	        newCours.setDateDebut(dateDebut);
	        newCours.setDateFin(dateFin);
	        newCours.setLib_cours(lib_cours);
	        newCours.setUrlVideo(videoCreatorName);
	        
	        return ResponseEntity.ok(coursService.updateCours(id ,newCours)).getBody();
	    }
	 
	 
	//downloadImage
		 @GetMapping("/{urlImage}/download")
		 public ResponseEntity<Resource> downloadImage(@PathVariable String urlImage)throws IOException{
			 
			 Resource file = serviceFile.downloadFile(urlImage);
			    Path path = file.getFile()
			            .toPath();

			    return ResponseEntity.ok()
			            .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
			            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename() + "\"")
			            .body(file);
		    }
		  
		 @GetMapping("getCoursById/{id}")
		    public ResponseEntity<Cours> getCoursById(@PathVariable("id") Long id) {
		        Optional<Cours> cours = coursService.getCoursById(id);
		        if (cours.isPresent()) {
		            return ResponseEntity.ok(cours.get());
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }
	
}
