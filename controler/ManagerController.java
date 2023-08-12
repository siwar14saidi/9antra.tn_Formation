package tn.antraFormationSpringBoot.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.repository.ManagerRepository;
import tn.antraFormationSpringBoot.services.EmailService;
import tn.antraFormationSpringBoot.services.FileService;
import tn.antraFormationSpringBoot.services.ManagerService;

@RestController	
@RequestMapping("/Manager")
@CrossOrigin(origins = "http://localhost:4200")
public class ManagerController {

	String subPath = "manager";
    String subPathFile = "manager/file";
    String subPathCreator = "manager/creator";
    
	@Autowired
	ManagerService mangService;
	
	@Autowired
	 FileService serviceFile;
	
	@Autowired
	  EmailService emailService;
	
	@Autowired
	ManagerRepository repManager;
	
	
	 @GetMapping("/all")
	    public List<Manager> getManager(){
	        return mangService.getManager();
	    }
	 
	 
	 @PostMapping("/addManager")
		public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
		  manager.setRole("manager");
		    manager.setEtat(false);
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

		   
		    String cheminImage = "/upload/images/manager/" + randomIndex;
		    manager.setNomImage(cheminImage);

		    Manager newManager = mangService.createManager(manager);
		    return ResponseEntity.ok(newManager);
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
	 

	  @PutMapping("/editManager/{id}")
	  public ResponseEntity<Manager> updateManager( 
	          @PathVariable("id") Long id,
	          @RequestParam(name = "cinManager", required = false) Integer cinManager,
	          @RequestParam(name = "nom", required = false) String nom,
	          @RequestParam(name = "prenom", required = false) String prenom,
	          @RequestParam(name = "email", required = false) String email,
	          @RequestParam(name = "numero", required = false) Integer numero,
	          @RequestParam(name = "ville", required = false) String ville,
	          @RequestParam(name = "adresse", required = false) String adresse) {

	      Optional<Manager> managerOptional = mangService.getManagerById(id);
	      if (managerOptional.isEmpty()) {
	          return ResponseEntity.badRequest().build();
	      }

	      Manager manager = managerOptional.get();

	    

	      // Mettre à jour les autres attributs du manager
	      if (cinManager != null) {
	          manager.setCinManager(cinManager);
	      }
	      if (nom != null) {
	    	  manager.setNom(nom);
	      }
	      if (prenom != null) {
	    	  manager.setPrenom(prenom);
	      }
	      if (email != null) {
	    	  manager.setEmail(email);
	      }
	      if (numero != null) {
	    	  manager.setNumero(numero);
	      }
	  
	      if (ville != null) {
	    	  manager.setVille(ville);
	      }
	      if (adresse != null) {
	    	  manager.setAdresse(adresse);
	      }

	      // Enregistrer les modifications dans la base de données
	      Manager updatedManager = mangService.editManager(id, manager);
	      return ResponseEntity.ok(updatedManager);
	  } 

	  
	  @PostMapping("/send-password/{id}")
		 public ResponseEntity<String> sendPasswordByEmail(@PathVariable Long id) {
		     try {
		         // Récupérer l'adresse e-mail de manager
		         Optional<Manager> manager = repManager.findById(id);
		         if (manager.isEmpty() || manager.get().getEmail() == null) {
		             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("manager ou adresse e-mail non trouvés.");
		         }
		         String email = manager.get().getEmail();

		         // Vérifier si l'adresse e-mail est valide
		         String apikey = "2a99458545cf43b3b7b339ed19f39b44";
		         String apiUrl = "https://api.zerobounce.net/v2/validate";
		         String query = "?api_key=" + apikey + "&email=" + email;
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

		         // Envoyer l'e-maill
		         emailService.sendPasswordByEmailM(id);
		         return ResponseEntity.ok("E-mail envoyé avec succès !");
		     } catch (Exception e) {
		         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
		     }
		 }


	  @GetMapping("getManagerById/{id}")
	    public ResponseEntity<Manager> getManagerById(@PathVariable("id") Long id) {
	        Optional<Manager> manager = mangService.getManagerById(id);
	        if (manager.isPresent()) {
	            return ResponseEntity.ok(manager.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }   
	  
		 @PutMapping("/{id}/modifierEtat")
		    public Manager modifierEtat(@PathVariable Long id) {
		        Manager manager = repManager.findById(id).orElseThrow(() -> new RuntimeException("Case not found with id: " + id));
		        
		        if (manager.isEtat()) {
		            manager.setEtat(false);
		        } else {
		            manager.setEtat(true);
		        }
		        
		        return repManager.save(manager);
		    }
}
