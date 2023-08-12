package tn.antraFormationSpringBoot.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.repository.ManagerRepository;


@Service
public class ManagerService  implements IManagerService {

	@Autowired
	ManagerRepository mangRep;
	


	public Manager createManager(Manager manager) {
		 manager.setRole("manager");
	        manager.setEtat(false);
	        // Liste d'URLs d'images
		    List<String> imageUrls = Arrays.asList( 
		        "/upload/images/formateur/photoDeProfil1.jpg",
		        "/upload/images/formateur/photoDeProfil2.jpg",
		        "/upload/images/formateur/photoDeProfil3.jpg",
		        "/upload/images/formateur/photoDeProfil4.jpg",
		        "/upload/images/formateur/photoDeProfil5.jpg",
		        "/upload/images/formateur/photoDeProfil6.jpg"
		    );

		    // Génération d'un index aléatoire
		    Random random = new Random();
		    int randomIndex = random.nextInt(imageUrls.size());
		    String randomImageUrl = imageUrls.get(randomIndex);

		  
	        String nomImage = randomImageUrl ;
	        String cheminImage =  nomImage;
	        manager.setNomImage(cheminImage);
	        Manager managerEnregistre = mangRep.save(manager);

	    	String password = generateRandomPassword();
	    	String encodedPassword = Base64.encodeBase64String(password.getBytes());

	    	manager.setMotDePasse(encodedPassword);
	        return mangRep.save(manager);
	}


			public String generateRandomPassword() {
			    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			    StringBuilder sb = new StringBuilder();
			    Random random = new Random();
			    int length = 8;
			    for (int i = 0; i < length; i++) {
			        int index = random.nextInt(chars.length());
			        sb.append(chars.charAt(index));
			    }
			    String password = sb.toString();
			    return password;
			}

			

			@Override
			public Optional<Manager> getManagerById(Long id) {
				// TODO Auto-generated method stub
				return mangRep.findById(id);
			}

			

			@Override
			public String getMotDePasseNonCrypte(Long id) {
				// TODO Auto-generated method stub
				 Optional<Manager> manager = mangRep.findById(id);
				    if (manager.isEmpty() || manager.get().getMotDePasse() == null) {
				        return null;
				    }
				    byte[] decodedBytes = Base64.decodeBase64(manager.map(Manager::getMotDePasse).get());
				    try {
				        return new String(decodedBytes, "UTF-8");
				    } catch (UnsupportedEncodingException e) {
				        e.printStackTrace();
				        return null;
				    }
			}


			@Override
			public List<Manager> getManager() {
				// TODO Auto-generated method stub
				return mangRep.findAll();
			}

			
	@Override
	public Manager editManager(Long id, Manager newManager) {
		 // Vérifier que newManager n'est pas null
	     Objects.requireNonNull(newManager, "newManager cannot be null");
	
	    // Chercher le manager à modifier par son ID
	    Optional<Manager> optionalManager = mangRep.findById(id);
	    if (optionalManager.isPresent()) {
	        Manager manager = optionalManager.get();

	        // Récupérer le manager existant dans la base de données
	        Manager existingManager = mangRep.findById(manager.getId()).orElseThrow(() -> new RuntimeException("Manager not found with ID: " + manager.getId()));

	        // Modifier les propriétés du manager avec les nouvelles valeurs
	        BeanUtils.copyProperties(newManager, manager, "id", "motDePasse", "cinManager", "nomImage");

	        // Modifier le mot de passe si nécessaire
	        if (newManager.getMotDePasse() != null) {
	            String encodedPassword = Base64.encodeBase64String(newManager.getMotDePasse().getBytes());
	            manager.setMotDePasse(encodedPassword);
	        }

	        // Enregistrer les modifications dans la base de données 
	        Manager updatedManager = mangRep.save(manager);
	        System.out.println("Les modifications ont été enregistrées dans la base de données : " + updatedManager);
	        return updatedManager;
	    } else {
	        // Lever une exception si le manager n'existe pas
	        throw new RuntimeException("Manager not found with ID: " + id);
	    }
	}


	public boolean verifyCredentials(String email, String password) {
	    Manager manager = findByEmail(email);
	    if (manager != null) {
	        // Vérifiez le mot de passe 
	        return password.equals(manager.getMotDePasse());
	    }
	    return false;
	}

	public Manager findByEmail(String email) {
	    return mangRep.findByEmail(email);
	}
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public Manager verifierManager(String email, String motDePasse) {
	    // Rechercher l'apprenant par e-mail dans la base de données
	    Manager manager = mangRep.findByEmail(email);

	 // Vérifier si l'apprenant existe et si le mot de passe correspond
	    if (manager != null && decodeBase64(manager.getMotDePasse()).equals(motDePasse)) {
	        return manager; // L'apprenant est trouvé et le mot de passe est correct
	    }
	    return null; // Aucun apprenant correspondant trouvé ou mot de passe incorrect
	}

private String decodeBase64(String encodedString) {
    byte[] decodedBytes = Base64.decodeBase64(encodedString);
    return new String(decodedBytes);
}
	

}
