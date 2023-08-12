package tn.antraFormationSpringBoot.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.repository.FormateurRepository;
import tn.antraFormationSpringBoot.services.EmailService;
import tn.antraFormationSpringBoot.services.FileService;
import tn.antraFormationSpringBoot.services.FormateurService;

@RestController	
@RequestMapping("/Formateur")
@CrossOrigin(origins = "http://localhost:4200")
public class FormateurController {


	String subPath = "formateur";
    String subPathFile = "formateur/file";
    String subPathCreator = "formateur/creator";
    
	@Autowired
	 FormateurService formateurServ;
	
	@Autowired
	 FileService serviceFile;
	
	@Autowired
	  EmailService emailService;
	
	@Autowired
	FormateurRepository formateurRepository;
	
	 @GetMapping("/all")
	    public List<Formateur> getFormateur(){
	        return formateurServ.getFormateur();
	    }
	 
	 
	/* @PostMapping("/addFormateur")
	 public ResponseEntity<Formateur> createFormateur(
	         @RequestParam(name = "cinUser", required = false) int cinUser,
	         @RequestParam(name = "nom", required = false) String nom,
	         @RequestParam(name = "prenom", required = false) String prenom,
	         @RequestParam(name = "email", required = false) String email,
	         @RequestParam(name = "numero", required = false) int numero,
	         @RequestParam(name = "formation", required = false) String formation,
	         @RequestParam(name = "ville", required = false) String ville,
	         @RequestParam(name = "adresse", required = false) String adresse,
	         @RequestParam(name = "cv", required = false) MultipartFile cv) {
	     
		 String imageCreatorName = "";
		 if (cv != null) {
		        imageCreatorName = serviceFile.saveFile(cv, this.subPathCreator);
		    }

	     Formateur formateur = new Formateur();
	     formateur.setCinUser(cinUser);
	     formateur.setNom(nom);
	     formateur.setPrenom(prenom);
	     formateur.setEmail(email);
	     formateur.setNumero(numero);
	     formateur.setFormation(formation);
	     formateur.setVille(ville);
	     formateur.setAdresse(adresse);
	     formateur.setCv(imageCreatorName);
	     formateur.setRole("formateur");
	     formateur.setEtat(false);
	     
	     if (formateur.getNomImage() == null || formateur.getNomImage().isEmpty()) {
	         formateur.setNomImage("photoDeProfil.jpg");
	     }
	     
	    
	     return ResponseEntity.ok(formateurServ.createFormateur(formateur));
	 }*/


		    
	 @PostMapping("/addFormateur")
	 public ResponseEntity<Formateur> createFormateur(
	     @RequestParam(name = "cinUser", required = false) int cinUser,
	     @RequestParam(name = "nom", required = false) String nom,
	     @RequestParam(name = "prenom", required = false) String prenom,
	     @RequestParam(name = "email", required = false) String email,
	     @RequestParam(name = "numero", required = false) int numero,
	     @RequestParam(name = "formation", required = false) String formation,
	     @RequestParam(name = "ville", required = false) String ville,
	     @RequestParam(name = "adresse", required = false) String adresse,
	     @RequestParam(name = "cv", required = false) MultipartFile cv) {

	     String imageCreatorName = "";
	     if (cv != null) {
	         imageCreatorName = serviceFile.saveFile(cv, this.subPathCreator);
	     }

	     Formateur formateur = new Formateur();
	     formateur.setCinUser(cinUser);
	     formateur.setNom(nom);
	     formateur.setPrenom(prenom);
	     formateur.setEmail(email);
	     formateur.setNumero(numero);
	     formateur.setFormation(formation);
	     formateur.setVille(ville);
	     formateur.setAdresse(adresse);
	     formateur.setCv(imageCreatorName);
	     formateur.setRole("formateur");
	     formateur.setEtat(false);

	     // Liste d'URLs d'images
	     List<String> imageUrls = Arrays.asList( 
	    		 "photoDeProfil1.jpg",
	    		 "photoDeProfil2.jpg",
	    		 "photoDeProfil3.jpg",
	    		 "photoDeProfil4.jpg",
	    		 "photoDeProfil5.jpg",
	    		 "photoDeProfil6.jpg"			 
	     );

	     // Génération d'un index aléatoire
	     Random random = new Random();
	     int randomIndex = random.nextInt(imageUrls.size());
	     String randomImageUrl = imageUrls.get(randomIndex);

	     // Utilisation de l'URL aléatoire pour l'image
	     formateur.setNomImage(randomImageUrl);

	     return ResponseEntity.ok(formateurServ.createFormateur(formateur));
	 }

		//downloadImage
	 @GetMapping("/{NomImage}/download")
	 public ResponseEntity<Resource> downloadImage(@PathVariable String NomImage) throws IOException{
		    Resource file = serviceFile.downloadFile(NomImage);
		    if (file == null) {
		        // renvoie une réponse d'erreur appropriée
		    }

		    Path path = file.getFile().toPath();

		    return ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
		            .body(file);
		}

	
	 
	 
	  @GetMapping("getFormateurById/{id}")
	    public ResponseEntity<Formateur> getFormateurById(@PathVariable("id") Long id) {
	        Optional<Formateur> formateur = formateurServ.getFormateurById(id);
	        if (formateur.isPresent()) {
	            return ResponseEntity.ok(formateur.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }   
	  
	  
	   //test  
	  
	  
	  @PutMapping("/editFormateur/{id}")
	  public ResponseEntity<Formateur> updateFormateur( 
	          @PathVariable("id") Long id,
	          @RequestPart(name = "nomImage", required = false) MultipartFile nomImage,
	          @RequestParam(name = "cinUser", required = false) Integer cinUser,
	          @RequestParam(name = "nom", required = false) String nom,
	          @RequestParam(name = "prenom", required = false) String prenom,
	          @RequestParam(name = "email", required = false) String email,
	          @RequestParam(name = "numero", required = false) Integer numero,
	          @RequestParam(name = "formation", required = false) String formation,
	          @RequestParam(name = "ville", required = false) String ville,
	          @RequestParam(name = "adresse", required = false) String adresse,
	          @RequestPart(name = "cv", required = false) MultipartFile cv) {

	      Optional<Formateur> formateurOptional = formateurServ.getFormateurById(id);
	      if (formateurOptional.isEmpty()) {
	          return ResponseEntity.badRequest().build();
	      }

	      Formateur formateur = formateurOptional.get();

	      String ancienNomFichierCV = formateur.getCv();

   
	      if (cv != null) {
	          // Supprimer l'ancien fichier CV s'il existe
	          if (!ancienNomFichierCV.equals("")) {
	              serviceFile.deleteFile(this.subPathCreator + "/" + ancienNomFichierCV);
	          }
	          // Enregistrer le nouveau fichier CV
	          String nouveauNomFichierCV = serviceFile.saveFile(cv, this.subPathCreator);
	          // Mettre à jour le nom du fichier CV dans le formateur
	          formateur.setCv(nouveauNomFichierCV);
	      }
 
	 
	      // Mettre à jour les autres attributs du formateur
	      if (cinUser != null) {
	          formateur.setCinUser(cinUser);
	      }
	      if (nom != null) {
	          formateur.setNom(nom);
	      }
	      if (prenom != null) {
	          formateur.setPrenom(prenom);
	      }
	      if (email != null) {
	          formateur.setEmail(email);
	      }
	      if (numero != null) {
	          formateur.setNumero(numero);
	      }
	      if (formation != null) {
	          formateur.setFormation(formation);
	      }
	      if (ville != null) {
	          formateur.setVille(ville);
	      }
	      if (adresse != null) {
	          formateur.setAdresse(adresse);
	      }

	      // Enregistrer les modifications dans la base de données
	      Formateur updatedFormateur = formateurServ.editFormateur(id, formateur);
	      return ResponseEntity.ok(updatedFormateur);
	  }

	   
	   
	 /* @PutMapping("/editFormateur/{id}")
	  public Formateur updateFormateur(
	          @RequestParam(name = "cinUser", required = false) int cinUser,
	          @RequestParam(name = "nom", required = false) String nom,
	          @RequestParam(name = "prenom", required = false) String prenom,
	          @RequestParam(name = "email", required = false) String email,
	          @RequestParam(name = "numero", required = false) int numero,
	          @RequestParam(name = "formation", required = false) String formation,
	          @RequestParam(name = "ville", required = false) String ville,
	          @RequestParam(name = "adresse", required = false) String adresse,
	          @RequestParam(name = "cv", required = false) MultipartFile cv,
	          @PathVariable("id") Long id)
	  {
	      Optional<Formateur> formateurOptional = formateurServ.getFormateurById(id);
	      if (formateurOptional.isEmpty()) {
	    	  ResponseEntity.badRequest().build();
	      }

	      Formateur formateur = formateurOptional.get();

	      String ancienNomFichierCV = formateur.getCv();

	      if (cv != null) {
	          // Supprimer l'ancien fichier CV s'il existe
	          if (!ancienNomFichierCV.equals("")) {
	              serviceFile.deleteFile(this.subPathCreator + "/" + ancienNomFichierCV);
	          }
	          // Enregistrer le nouveau fichier CV
	          String nouveauNomFichierCV = serviceFile.saveFile(cv, this.subPathCreator);
	          // Mettre à jour le nom du fichier CV dans le formateur
	          formateur.setCv(nouveauNomFichierCV);
	      }

	      // Mettre à jour les autres attributs du formateurs
	      formateur.setNom(nom);
	      formateur.setPrenom(prenom);
	      formateur.setEmail(email);
	      formateur.setNumero(numero);
	      formateur.setFormation(formation);
	      formateur.setVille(ville);
	      formateur.setAdresse(adresse);

	      // Enregistrer les modifications dans la base de données
	      return formateurServ.editFormateur(id, formateur);     
	  } */ 

		 
	  @PostMapping("/send-password/{id}")
	  public ResponseEntity<String> sendPasswordByEmail(@PathVariable Long id) {
	      try {
	          // Récupérer l'adresse e-mail du formateur
	          Optional<Formateur> formateur = formateurRepository.findById(id);
	          if (formateur.isEmpty() || formateur.get().getEmail() == null) {
	              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formateur ou adresse e-mail non trouvés.");
	          }
	          String email = formateur.get().getEmail();

	          // Vérifier si l'adresse e-mail est valide
	          String apikey =  "14cd6e64c453468b983939d64347325c";
	          String apiUrl = "https://api.zerobounce.net/v2/validate";
	          String query = "?api_key=" + apikey + "&email=" + URLEncoder.encode(email, "UTF-8");

	          URL url = new URL(apiUrl + query);
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          conn.setRequestMethod("GET");
	          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	          StringBuilder result = new StringBuilder();
	          String line;
	          while ((line = rd.readLine()) != null) {
	              result.append(line);
	          }
	          rd.close();

	          // Afficher la réponse de l'API pour le débogage
	          System.out.println("Réponse de l'API : " + result.toString());

	          // Vérifier si l'adresse e-mail est valide
	          JSONObject jsonObject = new JSONObject(result.toString());
	          String status = jsonObject.optString("status");
	          boolean isEmailValid = status.equals("valid");

	          if (!isEmailValid) {
	              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'adresse e-mail est invalide ou n'existe pas.");
	          }

	          // Envoyer l'e-mail
	          emailService.sendPasswordByEmailF(id); 
	          return ResponseEntity.ok("E-mail envoyé avec succès !");
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
	      }
	  }

	 
	 
	 
	 @PutMapping("/{id}/modifierEtat")
	    public Formateur modifierEtat(@PathVariable Long id) {
	        Formateur formateur = formateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Case not found with id: " + id));
	        
	        if (formateur.isEtat()) {
	            formateur.setEtat(false);
	        } else {
	            formateur.setEtat(true);
	        }
	        
	        return formateurRepository.save(formateur);
	    }
	 
	 
	 
}