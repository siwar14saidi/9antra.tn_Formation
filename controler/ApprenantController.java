package tn.antraFormationSpringBoot.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Cours;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Groupe;
import tn.antraFormationSpringBoot.repository.ApprenantRepository;
import tn.antraFormationSpringBoot.repository.GroupeRepository;
import tn.antraFormationSpringBoot.services.ApprenantService;
import tn.antraFormationSpringBoot.services.EmailService;
import tn.antraFormationSpringBoot.services.FileService;

@RestController	
@RequestMapping("/Apprenant")
@CrossOrigin(origins = "http://localhost:4200")
public class ApprenantController {

	String subPath = "apprenant";
    String subPathFile = "apprenant/file";
    String subPathCreator = "apprenant/creator";
    
	@Autowired
	 ApprenantService appServ;
	
	@Autowired
	 FileService serviceFile;
	
	@Autowired
	  EmailService emailService;
	
	@Autowired
	ApprenantRepository apprenantRepository;
	@Autowired
	GroupeRepository grpRep;
	
	
	 @GetMapping("/all")
	    public List<Apprenant> getApprenant(){
	        return appServ.getApprenant();
	    }
	 
	 
	 
	 
	
	 
	 /*
	 
	@PostMapping("/addApprenant")
	public ResponseEntity<Apprenant> createApprenant(@RequestBody Apprenant apprenant) {
	    apprenant.setRole("apprenant");
	    apprenant.setEtat(false);
	    if (apprenant.getNomImage() == null || apprenant.getNomImage().isEmpty()) {
	        apprenant.setNomImage("photoDeProfil.jpg");
	    }
	    
	    
	    Apprenant newApprenant = appServ.createApprenant(apprenant);
	    return ResponseEntity.ok(newApprenant);
	}
	
	*/
	 
	
	 @PostMapping("/addApprenant")
	 public ResponseEntity<Apprenant> createApprenant(@RequestBody Apprenant apprenant) {
	     // Vérifier si l'apprenant existe déjà
	     Apprenant existingApprenant = appServ.getApprenantByCin(appServ.getApprenant(), apprenant.getCinUser());
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

	     if (existingApprenant != null && existingApprenant.getEmail().equals(apprenant.getEmail())) {
	         // L'apprenant existe déjà avec le même cin et le même email,
	         // affecter le même mot de passe et la même image de profil
	         apprenant.setId(null); // Assurez-vous que l'ID est null pour créer une nouvelle inscription
	         apprenant.setMotDePasse(existingApprenant.getMotDePasse()); // Utilisez le même mot de passe
	         apprenant.setNomImage(existingApprenant.getNomImage()); // Utilisez la même image de profil
	     } else {
	         apprenant.setRole("apprenant");
	         apprenant.setEtat(false);

	         if (apprenant.getNomImage() == null || apprenant.getNomImage().isEmpty()) {
	             apprenant.setNomImage(randomImageUrl);
	         }
	     }

	     Apprenant newApprenant = appServ.createApprenant(apprenant);
	     return ResponseEntity.ok(newApprenant);
	 }

	 @RequestMapping(method = RequestMethod.GET, value = "/Apprenant")
	 public String testController() {
	     return "hello";
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

		 @PostMapping("/send-password/{id}")
		 public ResponseEntity<String> sendPasswordByEmail(@PathVariable Long id) {
		     try {
		         // Récupérer l'adresse e-mail de l'apprenant
		         Optional<Apprenant> apprenant = apprenantRepository.findById(id);
		         if (apprenant.isEmpty() || apprenant.get().getEmail() == null) {
		             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Apprenant ou adresse e-mail non trouvés.");
		         }
		         String email = apprenant.get().getEmail();
	
		         // Vérifier si l'adresse e-mail est valide
		         String apikey = "14cd6e64c453468b983939d64347325c";
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
		         emailService.sendPasswordByEmail(id);
		         return ResponseEntity.ok("E-mail envoyé avec succès !");
		     } catch (Exception e) {
		         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
		     }
		 }

	 @PutMapping("/editappgrp/{id}")
	 public ResponseEntity<Apprenant> updateAppgrp(@PathVariable Long id, @RequestBody Map<String, Long> requestBody) {
	     Optional<Apprenant> optionalApprenant = apprenantRepository.findById(id);
	     if (optionalApprenant.isPresent()) {
	         Apprenant apprenant = optionalApprenant.get();
	         Long groupeId = requestBody.get("groupe");
	         
	         Groupe groupe = new Groupe();
	         groupe.setId(groupeId);
	         
	         apprenant.setGroupe(groupe);
	         
	         Apprenant updatedApprenant = apprenantRepository.save(apprenant);
	         
	         return ResponseEntity.ok().body(updatedApprenant);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }



	 @PutMapping("/editApprenant/{id}")
	 public ResponseEntity<Apprenant> editApprenant(@PathVariable("id") Long id,
	          @RequestBody Apprenant newApprenant) {

	     Apprenant updatedApprenant = appServ.editApprenant(id, newApprenant);
	     return ResponseEntity.ok(updatedApprenant);
	 }

	 
	 @GetMapping("/GetApprenantsById/{id}")
	 public ResponseEntity<Apprenant> getApprenantById(@PathVariable("id") Long id) {
	     Apprenant apprenant = appServ.getApprenantById(apprenantRepository.findAll(), id);
	     if (apprenant == null) {
	         return ResponseEntity.notFound().build();
	     }
	     return ResponseEntity.ok(apprenant);
	 }
	 
	 @GetMapping("/GetApprenantsByCin/{cinUser}")
	 public ResponseEntity<Apprenant> getApprenantByCin(@PathVariable("cinUser") int cinUser) {
	     Apprenant apprenant = appServ.getApprenantByCin(apprenantRepository.findAll(), cinUser);
	     if (apprenant == null) {
	         return ResponseEntity.notFound().build();
	     }
	     return ResponseEntity.ok(apprenant);
	 }
	 

	 
	 
	 
	/* 
	  @PutMapping("/{id}/modifierEtat")
	    public Apprenant modifierEtat(@PathVariable Long id) {
	        Apprenant apprenant = apprenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Case not found with id: " + id));
	        
	        if (apprenant.isEtat()) {
	            apprenant.setEtat(false);
	        } else {
	            apprenant.setEtat(true);
	        }
	        
	        return apprenantRepository.save(apprenant);
	    }
	}*/
	 @PutMapping("/{id}/modifierEtat")
	 public Apprenant modifierEtat(@PathVariable Long id) {
	     Apprenant apprenant = apprenantRepository.findById(id)
	             .orElseThrow(() -> new RuntimeException("Apprenant not found with id: " + id));
	     
	     List<Apprenant> apprenantsByCinAndEmail = apprenantRepository.findByCinUserAndEmail(apprenant.getCinUser(), apprenant.getEmail());
	     
	     for (Apprenant app : apprenantsByCinAndEmail) {
	         if (!app.getId().equals(id)) {
	             if (app.isEtat()) {
	                 app.setEtat(false);
	             } else {
	                 app.setEtat(true);
	             }
	             apprenantRepository.save(app);
	         }
	     }
	     
	     if (apprenant.isEtat()) {
	         apprenant.setEtat(false);
	     } else {
	         apprenant.setEtat(true);
	     }
	     
	     return apprenantRepository.save(apprenant);
	 }
	 
	 @Autowired
	 private ApprenantService apprenantService;

	 // ...

	 @GetMapping("/details/{id}")
	 public ResponseEntity<Apprenant> getApprenantDetails(@PathVariable Long id) {
	     Apprenant apprenant = apprenantService.getAppById(id);
	     if (apprenant != null) {
	         return ResponseEntity.ok(apprenant);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }


}



